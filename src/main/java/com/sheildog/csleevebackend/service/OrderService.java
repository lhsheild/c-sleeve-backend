package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.core.LocalUser;
import com.sheildog.csleevebackend.core.enumeration.OrderStatus;
import com.sheildog.csleevebackend.core.money.IMoneyDiscount;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.exception.http.ForbiddenException;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.logic.CouponChecker;
import com.sheildog.csleevebackend.logic.OrderChecker;
import com.sheildog.csleevebackend.model.*;
import com.sheildog.csleevebackend.repository.CouponRepository;
import com.sheildog.csleevebackend.repository.OrderRepository;
import com.sheildog.csleevebackend.repository.SkuRepository;
import com.sheildog.csleevebackend.repository.UserCouponRepository;
import com.sheildog.csleevebackend.util.CommonUtil;
import com.sheildog.csleevebackend.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author a7818
 */
@Service
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount moneyDiscount;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${c-sleeve-backend.order.max-sku-limit}")
    private Integer maxSkuLimit;

    @Value("${c-sleeve-backend.order.pay-time-limit}")
    private Integer payTimeLimit;

    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ParameterException(50011);
        }
        List<Long> skuIdList = orderDTO.getSkuInfoList().stream()
                .map(s -> {
                    return s.getId();
                })
                .collect(Collectors.toList());

        List<Sku> skuList = skuService.getSkuListByIds(skuIdList);

        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40004));
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponIdAndStatus(uid, couponId, 1).orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, moneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(
                orderDTO,
                skuList,
                couponChecker,
                this.maxSkuLimit
        );
        orderChecker.isOk();
        return orderChecker;
    }

    private void writeOffCoupon(long couponId, Long oid, Long uid) {
        int result = this.userCouponRepository.writeOff(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        Calendar now = Calendar.getInstance();
        Calendar nowClone = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSomeSeconds(nowClone, this.payTimeLimit).getTime();
        String orderNo = OrderUtil.makeOrderNo();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(Long.valueOf(orderChecker.getTotalCount()))
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .placedTime(now.getTime())
                .expiredTime(expiredTime)
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
//        order.setCreateTime(now.getTime());
        this.orderRepository.save(order);

        //reduceStock
        this.reduceStock(orderChecker);
        //核销优惠券
        Long couponId = -1L;
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
            couponId = orderDTO.getCouponId();
        }
        //信息加入延迟消息队列
        this.sendToRedis(order.getId(), uid, couponId);
        return order.getId();
    }

    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.value(), uid, pageable);
    }

    public Page<Order> getByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if (status == OrderStatus.ALL.value()) {
            return this.orderRepository.findByUserId(uid, pageable);
        }
        return this.orderRepository.findByUserIdAndStatus(uid, status, pageable);
    }

    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findFirstByUserIdAndId(uid, oid);
    }

    public void updateOrderPrepayId(Long orderId, String prepayId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException(50009));
        order.setPrepayId(prepayId);
        this.orderRepository.save(order);
    }

    private void sendToRedis(Long oid, Long uid, Long couponId) {
        String key = oid.toString() + "," + uid.toString() + "," + couponId.toString();
        try {
            stringRedisTemplate.opsForValue().set(key, "1", this.payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

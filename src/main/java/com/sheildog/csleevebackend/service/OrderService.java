package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.core.enumeration.OrderStatus;
import com.sheildog.csleevebackend.core.money.IMoneyDiscount;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.logic.CouponChecker;
import com.sheildog.csleevebackend.logic.OrderChecker;
import com.sheildog.csleevebackend.model.*;
import com.sheildog.csleevebackend.repository.CouponRepository;
import com.sheildog.csleevebackend.repository.OrderRepository;
import com.sheildog.csleevebackend.repository.SkuRepository;
import com.sheildog.csleevebackend.repository.UserCouponRepository;
import com.sheildog.csleevebackend.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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

    @Value("${c-sleeve-backend.order.max-sku-limit}")
    private Integer maxSkuLimit;

    @Value("${c-sleeve-backend.order.max-sku-limit}")
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
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId).orElseThrow(() -> new NotFoundException(50006));
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
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        this.orderRepository.save(order);

        //reduceStock
        this.reduceStock(orderChecker);
        //核销优惠券
        //信息加入延迟消息队列
        return order.getId();
    }
}

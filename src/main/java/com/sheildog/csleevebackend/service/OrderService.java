package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.core.money.IMoneyDiscount;
import com.sheildog.csleevebackend.dto.OrderDTO;
import com.sheildog.csleevebackend.dto.SkuInfoDTO;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.logic.CouponChecker;
import com.sheildog.csleevebackend.logic.OrderCheck;
import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.model.Sku;
import com.sheildog.csleevebackend.model.UserCoupon;
import com.sheildog.csleevebackend.repository.CouponRepository;
import com.sheildog.csleevebackend.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Value("${c-sleeve-backend.order.max-sku-limit}")
    private Integer maxSkuLimit;

    @Value("${c-sleeve-backend.order.max-sku-limit}")
    private Integer payTimeLimit;

    public OrderCheck isOk(Long uid, OrderDTO orderDTO) {
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
        OrderCheck orderCheck = new OrderCheck(
                orderDTO,
                skuList,
                couponChecker,
                this.maxSkuLimit
        );
        orderCheck.isOk();
        return orderCheck;
    }
}

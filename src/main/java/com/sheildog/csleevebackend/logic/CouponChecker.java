package com.sheildog.csleevebackend.logic;

import com.sheildog.csleevebackend.bo.SkuOrderBO;
import com.sheildog.csleevebackend.core.enumeration.CouponType;
import com.sheildog.csleevebackend.core.money.HalfEvenRound;
import com.sheildog.csleevebackend.core.money.IMoneyDiscount;
import com.sheildog.csleevebackend.exception.http.ForbiddenException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.model.UserCoupon;
import com.sheildog.csleevebackend.util.CommonUtil;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
    private IMoneyDiscount moneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount moneyDiscount) {
        this.coupon = coupon;
        this.moneyDiscount = moneyDiscount;
    }

    public void isOk() {
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLine(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;

        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
                serverFinalTotalPrice = this.moneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            default:
                throw new ParameterException(40009);
        }

        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        BigDecimal orderCategoryPrice;

        if (this.coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = this.coupon.getCategoryList().stream().map(
                    r -> r.getId()
            ).collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList, cidList);
        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);

        }
    }

    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        BigDecimal sum = cidList.stream()
                .map(cid -> this.getSumByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return sum;
    }

    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList, Long cid) {
        BigDecimal sum = skuOrderBOList.stream()
                .filter(sku -> sku.getCategoryId().equals(cid))
                .map(sku -> sku.getTotalPrice())
                .reduce((bigDecimal, augend) -> bigDecimal.add(augend))
                .orElse(new BigDecimal("0"));
        return sum;
    }
}

package com.sheildog.csleevebackend.logic;

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

public class CouponChecker {

    private Coupon coupon;
    private UserCoupon userCoupon;
    private IMoneyDiscount moneyDiscount;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon, IMoneyDiscount moneyDiscount) {
        this.coupon = coupon;
        this.userCoupon = userCoupon;
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
        BigDecimal serverFinaleTotalPrice;
//        int compare = Integer.parseInt(null);

        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
                serverFinaleTotalPrice = this.moneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinaleTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinaleTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            default:
                throw new ParameterException(40009);
        }

        int compare = serverFinaleTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed() {

    }

//    public CouponChecker(Long couponId, Long uid){
//
//    }
}

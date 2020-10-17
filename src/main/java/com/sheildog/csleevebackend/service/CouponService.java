package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.core.enumeration.CouponStatus;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.model.Activity;
import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.model.UserCoupon;
import com.sheildog.csleevebackend.repository.ActivityRepository;
import com.sheildog.csleevebackend.repository.CouponRepository;
import com.sheildog.csleevebackend.repository.UserCouponRepository;
import com.sheildog.csleevebackend.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public void collectOneCoupon(Long uid, Long couponId) {
        this.couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40003));

        Activity activity = this.activityRepository.findByCouponListId(couponId).orElseThrow(() -> new NotFoundException(40010));

        Date now = new Date();
        if (!CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime())) {
            throw new ParameterException(40005);
        }

        this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId).ifPresent((uc) -> {
            throw new ParameterException(40006);
        });

        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCouponNew);
    }

    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyExpired(uid, now);
    }
}

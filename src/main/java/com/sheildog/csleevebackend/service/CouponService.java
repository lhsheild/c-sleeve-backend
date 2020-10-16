package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.repository.ActivityRepository;
import com.sheildog.csleevebackend.repository.CouponRepository;
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

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
//        return couponRepository.findByCategory(cid);
    }
}

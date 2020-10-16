package com.sheildog.csleevebackend.vo;

import com.sheildog.csleevebackend.model.Activity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO {
    private List<CouponPureVO> coupons;

//                    .map(CategoryPureVO::new)
//                    .map(r->{ return new CategoryPureVO(r);})
    public ActivityCouponVO(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
//                .stream().map(CouponPureVO::new)
                .stream().map(r->{return new CouponPureVO(r);})
                .collect(Collectors.toList());
    }
}


package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Activity;
import com.sheildog.csleevebackend.service.ActivityService;
import com.sheildog.csleevebackend.vo.ActivityCouponVO;
import com.sheildog.csleevebackend.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a7818
 */
@RestController
@RequestMapping("/activity")
@Validated
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(
            @PathVariable String name
    ){
        Activity activity = activityService.getByName(name);
        if (activity == null){
            throw new NotFoundException(40001);
        }
        ActivityPureVO actvityPureVO = new ActivityPureVO(activity);
        return actvityPureVO;
    }

    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityWithCoupons(
            @PathVariable String name
    ){
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        return new ActivityCouponVO(activity);
    }
}

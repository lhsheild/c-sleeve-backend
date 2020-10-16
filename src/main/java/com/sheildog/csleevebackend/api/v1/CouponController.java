package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.service.CouponService;
import com.sheildog.csleevebackend.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequestMapping("coupon")
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponListByCategory(
            @PathVariable Long cid
    ){
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
//            return Collections.emptyList();
            throw new NotFoundException(40002);
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList(){
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()){
//            return Collections.emptyList();
            throw new NotFoundException(40002);
        }
        return CouponPureVO.getList(coupons);
    }
}

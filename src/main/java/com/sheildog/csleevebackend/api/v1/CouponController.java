package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.core.LocalUser;
import com.sheildog.csleevebackend.core.UnifyResponse;
import com.sheildog.csleevebackend.core.enumeration.CouponStatus;
import com.sheildog.csleevebackend.core.interceptors.ScopeLevel;
import com.sheildog.csleevebackend.exception.CreateSuccess;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.model.Coupon;
import com.sheildog.csleevebackend.model.User;
import com.sheildog.csleevebackend.service.CouponService;
import com.sheildog.csleevebackend.vo.CouponCategoryVO;
import com.sheildog.csleevebackend.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @ScopeLevel()
    @PostMapping("/collect/{id}")
    public void collectCoupon(
            @PathVariable Long id
    ){
        Long uid = LocalUser.getUser().getId();
        couponService.collectOneCoupon(uid, id);
//        throw new CreateSuccess(0);
        UnifyResponse.createSuccess(0);
    }

    @ScopeLevel()
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponByStatus(
            @PathVariable Integer status
    ) {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;

        switch (CouponStatus.toType(status)){
            case AVAILABLE:
                couponList = this.couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = this.couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = this.couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }

        return CouponPureVO.getList(couponList);
    }

    @ScopeLevel()
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory(){
        User user = LocalUser.getUser();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if (coupons.isEmpty()){
            return Collections.emptyList();
        }
        List<CouponCategoryVO> couponCategoryVOS = coupons.stream().map(coupon -> {
            CouponCategoryVO vo = new CouponCategoryVO(coupon);
            return vo;
        }).collect(Collectors.toList());
        return couponCategoryVOS;
    }
}

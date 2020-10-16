package com.sheildog.csleevebackend.vo;

import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author a7818
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponPureVO {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVO(Object[] objects){
        Coupon coupon = (Coupon) objects[0];
        if (coupon == null){
            throw new NotFoundException(40001);
        }
        BeanUtils.copyProperties(coupon, this);
    }

    public CouponPureVO(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVO> getList(List<Coupon> couponList) {
        return couponList.stream()
                .map(r->{return new CouponPureVO(r);})
                .collect(Collectors.toList());
    }
}

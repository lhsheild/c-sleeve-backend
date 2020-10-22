package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findFirstByUserIdAndCouponIdAndStatus(Long uid, Long couponId, Integer status);

    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status = 2, uc.orderId = ?2\n" +
            "where uc.userId = ?3 and uc.couponId = ?1 and uc.status = 1 and uc.orderId is null ")
    int writeOff(Long couponId, Long oid, Long uid);
}

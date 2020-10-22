package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    @Modifying
    @Query("")
    int writeOff(Long couponId, Long oid, Long uid);
}

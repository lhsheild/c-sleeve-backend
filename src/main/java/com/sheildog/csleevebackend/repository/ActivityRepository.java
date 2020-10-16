package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByName(String name);
    Optional<Activity> findByCouponListId(Long couponId);
}

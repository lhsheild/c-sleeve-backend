package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    //    Coupon Coupon_Category Category
//    @Query(value = "select * from coupon\n" +
//            "join coupon_category\n" +
//            "on coupon.id = coupon_category.coupon_id\n" +
//            "join category\n" +
//            "on coupon_category.category_id = category.id\n" +
//            "join activity\n" +
//            "on activity.id = coupon.activity_id\n" +
//            "where category.id = ?1\n" +
//            "and activity.end_time > ?2\n" +
//            "and activity.start_time < ?2", nativeQuery = true)
    @Query("select c from Coupon c\n" +
            "join c.categoryList ca\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where ca.id = :cid\n" +
//            "and a.startTime < current_time ")
            "and a.startTime < :now \n" +
            "and a.endTime > :now\n")
    List<Coupon> findByCategory(@Param("cid") Long cid, @Param("now") Date now);
//    List<Coupon> findByCategory(@Param("cid") Long cid);
}

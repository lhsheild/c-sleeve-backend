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
            "and a.startTime < :now \n" +
            "and a.endTime > :now\n")
    List<Coupon> findByCategory(@Param("cid") Long cid, @Param("now") Date now);

    @Query("select c from Coupon c\n" +
            "join Activity a on c.activityId = a.id\n" +
            "where c.wholeStore = :isWholeStore\n" +
            "and a.startTime < :now\n" +
            "and a.endTime > :now")
    List<Coupon> findByWholeStore(@Param("isWholeStore") Boolean isWholeStore, @Param("now") Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where uc.status = 1\n" +
            "and u.id = :uid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null ")
    List<Coupon> findMyAvailable(@Param("uid") Long uid, @Param("now") Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null \n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(@Param("uid") Long uid, @Param("now") Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status <> 2\n" +
            "and uc.orderId is null \n" +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(@Param("uid") Long uid, @Param("now") Date now);
}

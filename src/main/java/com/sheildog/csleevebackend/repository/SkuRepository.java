package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query(value = "update Sku s set s.stock = s.stock - ?2\n" +
            "where s.id = ?1\n" +
            "and s.stock >= ?2")
    int reduceStock(Long sid, Long quantity);

    @Modifying
    @Query("update Sku s set s.stock=s.stock+(:quantity) where s.id = :sid")
    int recoverStock(@Param("sid") Long sid,
                     @Param("quantity") Long quantity);
}

package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.bo.OrderMessageBO;
import com.sheildog.csleevebackend.core.enumeration.OrderStatus;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import com.sheildog.csleevebackend.model.Order;
import com.sheildog.csleevebackend.repository.OrderRepository;
import com.sheildog.csleevebackend.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CouponBackService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

//    @EventListener
    @Transactional
    public void returnBack(OrderMessageBO bo) {
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();

        if (couponId == -1) {
            return;
        }

        Optional<Order> orderOptional = orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));

        if (order.getStatusEnum().equals(OrderStatus.PAID) || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            this.userCouponRepository.returnBack(couponId, uid);
        }
    }
}

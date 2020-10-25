package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.bo.OrderMessageBO;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import com.sheildog.csleevebackend.model.Order;
import com.sheildog.csleevebackend.repository.OrderRepository;
import com.sheildog.csleevebackend.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderCancelService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

//    @EventListener
    @Transactional
    public void cancel(OrderMessageBO messageBO) {
        if (messageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getOrderId());
    }

    private void cancel(Long oid) {
        Optional<Order> orderOptional = orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        int res = orderRepository.cancelOrder(oid);
        if (res != 1) {
            return;
        }
        order.getSnapItems().stream().forEach(i -> {
            skuRepository.recoverStock(i.getId(), i.getCount().longValue());
        });
    }
}

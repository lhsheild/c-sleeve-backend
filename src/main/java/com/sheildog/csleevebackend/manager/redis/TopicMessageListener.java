package com.sheildog.csleevebackend.manager.redis;

import com.sheildog.csleevebackend.bo.OrderMessageBO;
import com.sheildog.csleevebackend.service.CouponBackService;
import com.sheildog.csleevebackend.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TopicMessageListener implements MessageListener {
    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponBackService couponBackService;

    //订阅模式
//    private static ApplicationEventPublisher publisher;
//
//    @Autowired
//    public void setPublisher(ApplicationEventPublisher publisher){
//        TopicMessageListener.publisher = publisher;
//    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String expiredKey = new String(body);
        String topic = new String(channel);

        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);

        orderCancelService.cancel(orderMessageBO);
        couponBackService.returnBack(orderMessageBO);
//        TopicMessageListener.publisher.publishEvent(orderMessageBO);
    }
}

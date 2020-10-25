package com.sheildog.csleevebackend.manager.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

//@Component
public class ConsumerSchedule implements CommandLineRunner {

    @Value("${rocketmq.consumer.consumer-group}")
    private String consumerGroup;
    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;

    public ConsumerSchedule() {
    }

    public void messageListener() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.consumerGroup);
        consumer.setNamesrvAddr(this.namesrvAddr);
        consumer.subscribe("topictest", "*");
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            for (Message msg : messages) {
                System.out.println("消息：" + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}

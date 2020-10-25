package com.sheildog.csleevebackend.manager.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class ProducerSchedule {
    private DefaultMQProducer producer;

    @Value("${rocketmq.producer.producer-group}")
    private String producerGroup;
    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;
    @Value("${rocketmq.delay-level}")
    private Integer delayLevel;

    public ProducerSchedule() {
    }

    // 初始化过程中想做一些事情同时依赖springboot托管注入的成员变量
    @PostConstruct
    public void defaultMQProducer() {
        if (this.producer == null) {
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.namesrvAddr);
        }
        try {
            this.producer.start();
            System.out.println("------producer start------");

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public String send(String topic, String tag, String messageText) throws Exception {
        Message message = new Message(topic, tag, messageText.getBytes());
        message.setDelayTimeLevel(this.delayLevel);

//        try {
//            this.producer.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        SendResult res = this.producer.send(message);
        System.out.println(res.getMsgId());
        System.out.println(res.getSendStatus());
        return res.getMsgId();
    }
}

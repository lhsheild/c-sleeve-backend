package com.sheildog.csleevebackend.manager.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.awt.*;

@Configuration
public class MessageListenerConfiguration {
    @Value("${spring.redis.listen-pattern}")
    private String pattern;

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        Topic topic = new PatternTopic(this.pattern);
        redisMessageListenerContainer.addMessageListener(new TopicMessageListener(), topic);

        return redisMessageListenerContainer;
    }
}

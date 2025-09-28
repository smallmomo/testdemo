package com.example.demo.consumer;

import com.example.demo.model.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ConsumerA {

    @Bean
    public Consumer<Message> consumerA() {
        return message -> {
            System.out.println("ConsumerA 收到消息: " + message.getContent());
            System.out.println("路由键: " + message.getRouteKey());
        };
    }
}
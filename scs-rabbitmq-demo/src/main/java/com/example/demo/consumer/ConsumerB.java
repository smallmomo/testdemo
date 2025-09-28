package com.example.demo.consumer;

import com.example.demo.model.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ConsumerB {

    @Bean
    public Consumer<Message> consumerB() {
        return message -> {
            System.out.println("ConsumerB 收到消息: " + message.getContent());
            System.out.println("路由键: " + message.getRouteKey());
        };
    }
}
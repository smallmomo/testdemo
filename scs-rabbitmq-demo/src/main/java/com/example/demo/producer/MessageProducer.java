package com.example.demo.producer;

import com.example.demo.model.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MessageProducer {

    @Bean
    public Supplier<Message<?>> producer() {
        return () -> {
            // 这里仅作为示例，实际使用时应该通过服务方法调用来发送消息
            com.example.demo.model.Message message = new com.example.demo.model.Message(
                "测试消息内容",
                "A" // 或者 "B"，用于路由到不同的队列
            );
            
            return MessageBuilder
                    .withPayload(message)
                    .setHeader("routeKey", message.getRouteKey())
                    .build();
        };
    }
}
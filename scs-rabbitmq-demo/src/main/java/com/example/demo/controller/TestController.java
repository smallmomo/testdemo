package com.example.demo.controller;

import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamsBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private StreamsBuilder.StreamsBuilderFactoryBean streamsBuilder;

    @GetMapping("/send/{routeKey}")
    public String sendMessage(@PathVariable String routeKey) {
        Message message = new Message("测试消息内容-" + System.currentTimeMillis(), routeKey);
        
        MessageChannel producerChannel = streamsBuilder.getApplicationContext()
                .getBean("producer-out-0", MessageChannel.class);
        
        boolean sent = producerChannel.send(MessageBuilder
                .withPayload(message)
                .setHeader("routeKey", routeKey)
                .build());
                
        return sent ? "消息发送成功" : "消息发送失败";
    }
}
package com.example.scsrabbitmqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class ScsRabbitmqDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(ScsRabbitmqDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ScsRabbitmqDemoApplication.class, args);
    }

    // 1. Producer: Sends UserAction objects for two different users
    @Bean
    public Supplier<Flux<UserAction>> myProducer() {
        return () -> Flux.interval(Duration.ofSeconds(1))
                .map(i -> {
                    // Alternate between user-1 and user-2
                    String userId = "user-" + (i % 2 == 0 ? "1" : "2");
                    String action = "action-" + i;
                    return new UserAction(userId, action);
                });
    }

    // 2. Consumer: Receives UserAction objects and logs the processing thread
    @Bean
    public Consumer<UserAction> myConsumer() {
        return action -> {
            log.info("Thread: [{}], Received: {}", Thread.currentThread().getName(), action);
            // Simulate some work
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    // 3. A simple POJO for our message payload
    public static class UserAction {
        private String userId;
        private String action;

        // Default constructor for JSON deserialization
        public UserAction() {}

        public UserAction(String userId, String action) {
            this.userId = userId;
            this.action = action;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return "UserAction{" +
                    "userId='" + userId + '\'' +
                    ", action='" + action + '\'' +
                    '}';
        }
    }
}
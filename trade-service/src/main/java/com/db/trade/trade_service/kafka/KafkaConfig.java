package com.db.trade.trade_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic tradeTopic() {
        return new NewTopic("trade-topic", 1, (short) 1); // Partition count and replication factor
    }
}


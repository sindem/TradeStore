package com.db.trade.trade_service.kafka;

import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class KafkaTopicCreator {

    @Bean
    public CommandLineRunner createKafkaTopic() {
        return args -> {
            try (AdminClient adminClient = AdminClient.create(kafkaAdminConfig())) {
                // Create the "trade-topic" if it doesn't already exist
                NewTopic tradeTopic = new NewTopic("trade-topic", 1, (short) 1); // 1 partition, replication factor 1
                adminClient.createTopics(java.util.Collections.singletonList(tradeTopic));
            } catch (TopicExistsException e) {
                System.out.println("Topic already exists.");
            }
        };
    }

    private Properties kafkaAdminConfig() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return properties;
    }
}


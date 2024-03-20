package com.gevorg.demo.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopicConfiguration {

    @Value(value = "${spring.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.topic.book.name}")
    private String bookTopic;

    @Value(value = "${spring.kafka.topic.book.partitions}")
    private Integer bookPartitions;

    @Value(value = "${spring.kafka.topic.book.replicas}")
    private Integer bookReplicas;

    private NewTopic topicBook() {
        return TopicBuilder.name(bookTopic)
                .partitions(bookPartitions)
                .replicas(bookReplicas)
                .configs(getDefaultConfigProps())
                .build();
    }

    private Map<String, String> getDefaultConfigProps() {
        Map<String, String> props = new HashMap<>();
        props.put("retention.ms", "600000");
        props.put("retention.bytes", "50000000");
        return props;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {

        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);

        kafkaAdmin.createOrModifyTopics(
                topicBook()
        );

        return kafkaAdmin;
    }
}

package com.gevorg.demo.kafka.config;


import com.gevorg.demo.kafka.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.groupId}")
    private String groupId;
    @Value(value = "${spring.kafka.concurrency}")
    private Integer concurrency;

    @Value(value = "${spring.kafka.consumer.properties.max.poll.interval.ms}")
    private Integer maxPollInterval;

    @Value(value = "${spring.kafka.consumer.properties.max.poll.records}")
    private Integer maxPollRecords;

    @Value(value = "${spring.kafka.consumer.properties.partition.assignment.strategy}")
    private String strategy;

    @Value(value = "${spring.kafka.consumer.properties.heartbeat.interval.ms}")
    private String heartBeatInterval;

    @Bean
    public ConsumerFactory<String, Object> objectConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getDefaultProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookDto> bookDtoConcurrentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BookDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(objectConsumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;

    }

    private Map<String, Object> getDefaultProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartBeatInterval);
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, strategy);
        return props;
    }
}

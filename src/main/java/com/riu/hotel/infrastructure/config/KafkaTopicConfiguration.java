package com.riu.hotel.infrastructure.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class KafkaTopicConfiguration {

    @Bean(name = "kafkaAvailabilitySearchProcessingExecutor")
    public Executor kafkaAvailabilitySearchProcessingExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public NewTopic hotelAvailabilitySearchesTopic(
            @Value("${app.kafka.topics.hotel-availability-searches}") String topicName
    ) {
        log.info("Creando tópico: {}", topicName);
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }
}

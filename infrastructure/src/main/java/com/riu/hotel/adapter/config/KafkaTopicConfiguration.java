package com.riu.hotel.adapter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic hotelAvailabilitySearchesTopic(
            @Value("${app.kafka.topics.hotel-availability-searches}") String topicName
    ) {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }
}

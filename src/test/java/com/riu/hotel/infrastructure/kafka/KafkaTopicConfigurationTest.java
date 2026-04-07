package com.riu.hotel.infrastructure.kafka;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.riu.hotel.infrastructure.config.KafkaTopicConfiguration;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.TestPropertySource;

@SpringJUnitConfig(KafkaTopicConfiguration.class)
@TestPropertySource(properties = "app.kafka.topics.hotel-availability-searches=fixture_topic")
class KafkaTopicConfigurationTest {

    @Autowired
    private NewTopic hotelAvailabilitySearchesTopic;

    @Test
    void shouldRegisterTopicWithConfiguredName() {
        assertAll(
                () -> assertEquals("fixture_topic", hotelAvailabilitySearchesTopic.name()),
                () -> assertEquals(1, hotelAvailabilitySearchesTopic.numPartitions()),
                () -> assertEquals((short) 1, hotelAvailabilitySearchesTopic.replicationFactor()));
    }
}

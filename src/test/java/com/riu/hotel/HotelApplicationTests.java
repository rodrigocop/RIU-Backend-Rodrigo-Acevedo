package com.riu.hotel;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.infrastructure.out.persistence.AvailabilitySearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
class HotelApplicationTests {

    @MockBean
    private KafkaTemplate<String, AvailabilitySearch> kafkaTemplate;

    @MockBean
    private AvailabilitySearchRepository availabilitySearchRepository;

    @Test
    void contextLoads() {
    }
}

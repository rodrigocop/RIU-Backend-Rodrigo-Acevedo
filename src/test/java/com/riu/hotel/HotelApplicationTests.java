package com.riu.hotel;

import static org.assertj.core.api.Assertions.assertThat;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.infrastructure.out.persistence.AvailabilitySearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
class HotelApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private KafkaTemplate<String, AvailabilitySearch> kafkaTemplate;

    @MockBean
    private AvailabilitySearchRepository availabilitySearchRepository;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getBeanDefinitionNames()).isNotEmpty();
    }

    @Test
    void hotelApplicationEstáAnotadaComoSpringBoot() {
        assertThat(HotelApplication.class.getAnnotation(SpringBootApplication.class))
                .as("la clase principal debe ser @SpringBootApplication")
                .isNotNull();
        assertThat(HotelApplication.class.getAnnotation(SpringBootApplication.class).scanBasePackages())
                .containsExactly("com.riu.hotel");
    }
}

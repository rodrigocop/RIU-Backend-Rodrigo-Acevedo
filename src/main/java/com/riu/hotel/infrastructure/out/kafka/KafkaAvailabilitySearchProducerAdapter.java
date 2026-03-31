package com.riu.hotel.infrastructure.out.kafka;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.domain.port.out.AvailabilitySearchEventPublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Adaptador de salida: publica eventos de búsqueda hacia Kafka ({@link KafkaTemplate}).
 * El consumidor está en {@code com.riu.hotel.infrastructure.in.kafka}.
 */
@Slf4j
@Component
public class KafkaAvailabilitySearchProducerAdapter implements AvailabilitySearchEventPublisherPort {

    private final KafkaTemplate<String, AvailabilitySearch> kafkaTemplate;
    private final String hotelAvailabilitySearchesTopic;

    public KafkaAvailabilitySearchProducerAdapter(
            KafkaTemplate<String, AvailabilitySearch> kafkaTemplate,
            @Value("${app.kafka.topics.hotel-availability-searches}") String hotelAvailabilitySearchesTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.hotelAvailabilitySearchesTopic = hotelAvailabilitySearchesTopic;
    }

    @Override
    public void publish(AvailabilitySearch availabilitySearch) {
        try {
            kafkaTemplate
                    .send(hotelAvailabilitySearchesTopic, availabilitySearch.getSearchId(), availabilitySearch)
                    .get();

            log.info(
                    "Mensaje enviado al tópico {} con searchId={}",
                    hotelAvailabilitySearchesTopic,
                    availabilitySearch.getSearchId());
        } catch (Exception e) {
            log.error("Error enviando mensaje a Kafka para searchId={}", availabilitySearch.getSearchId(), e);
        }
    }
}

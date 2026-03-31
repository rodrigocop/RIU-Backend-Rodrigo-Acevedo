package com.riu.hotel.infrastructure.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.domain.port.in.RegisterAvailabilitySearchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Adaptador de entrada: consume el tópico de búsquedas y delega en el caso de uso de registro.
 * La publicación está en {@code com.riu.hotel.infrastructure.out.kafka}.
 */
@Slf4j
@Component
public class KafkaAvailabilitySearchConsumerAdapter {

    private final RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase;
    private final ObjectMapper objectMapper;

    public KafkaAvailabilitySearchConsumerAdapter(
            RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase,
            ObjectMapper objectMapper
    ) {
        this.registerAvailabilitySearchUseCase = registerAvailabilitySearchUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.hotel-availability-searches}",
            groupId = "${app.kafka.consumer.group-id}"
    )
    public void consume(String payload) {
        try {
            AvailabilitySearch availabilitySearch = objectMapper.readValue(payload, AvailabilitySearch.class);
            log.info(
                    "Mensaje recibido de Kafka: searchId={}, hotelId={}. Persistiendo…",
                    availabilitySearch.getSearchId(),
                    availabilitySearch.getHotelId());
            registerAvailabilitySearchUseCase.execute(availabilitySearch);
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar mensaje de Kafka", e);
        }
    }
}

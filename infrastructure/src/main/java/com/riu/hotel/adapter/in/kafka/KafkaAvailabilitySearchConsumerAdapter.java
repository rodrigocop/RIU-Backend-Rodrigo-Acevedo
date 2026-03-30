package com.riu.hotel.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.in.RegisterAvailabilitySearchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
                   // availabilitySearch.getSearchId(),
                    availabilitySearch.getHotelId());
            registerAvailabilitySearchUseCase.register(availabilitySearch);
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar mensaje de Kafka", e);
        }
    }
}

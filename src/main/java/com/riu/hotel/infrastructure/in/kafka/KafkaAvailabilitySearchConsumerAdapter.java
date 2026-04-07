package com.riu.hotel.infrastructure.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotel.application.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.domain.model.AvailabilitySearch;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaAvailabilitySearchConsumerAdapter {

    private final RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase;
    private final ObjectMapper objectMapper;
    private final Executor processingExecutor;

    public KafkaAvailabilitySearchConsumerAdapter(
            RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase,
            ObjectMapper objectMapper,
            @Qualifier("kafkaAvailabilitySearchProcessingExecutor") Executor processingExecutor
    ) {
        this.registerAvailabilitySearchUseCase = registerAvailabilitySearchUseCase;
        this.objectMapper = objectMapper;
        this.processingExecutor = processingExecutor;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.hotel-availability-searches}",
            groupId = "${app.kafka.consumer.group-id}"
    )
    public void consume(String payload) {
        processingExecutor.execute(() -> {
            try {
                AvailabilitySearch availabilitySearch =
                        objectMapper.readValue(payload, AvailabilitySearch.class);
                log.info(
                        "Mensaje recibido de Kafka: searchId={}, hotelId={}. Persistiendo…",
                        availabilitySearch.searchId(),
                        availabilitySearch.hotelId());

                registerAvailabilitySearchUseCase.execute(availabilitySearch);

            } catch (JsonProcessingException e) {
                log.error("Error al deserializar mensaje de Kafka", e);
            }
        });
    }
}

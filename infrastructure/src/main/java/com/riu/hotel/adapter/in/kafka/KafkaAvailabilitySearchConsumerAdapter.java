package com.riu.hotel.adapter.in.kafka;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.in.RegisterAvailabilitySearchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaAvailabilitySearchConsumerAdapter {

    private final RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase;

    public KafkaAvailabilitySearchConsumerAdapter(RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase) {
        this.registerAvailabilitySearchUseCase = registerAvailabilitySearchUseCase;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.hotel-availability-searches}",
            groupId = "${app.kafka.consumer.group-id}"
    )
    public void consume(AvailabilitySearch availabilitySearch) {
        log.info(
                "Mensaje recibido de Kafka (hotelId trazabilidad={}). Se inicia registro en Oracle (sin hotelId)",
                availabilitySearch.getHotelId()
        );
        registerAvailabilitySearchUseCase.register(availabilitySearch);
    }
}

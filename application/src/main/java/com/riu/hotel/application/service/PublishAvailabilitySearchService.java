package com.riu.hotel.application.service;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.port.out.AvailabilitySearchEventPublisherPort;

import java.util.UUID;


public class PublishAvailabilitySearchService implements PublishAvailabilitySearchUseCase {

    private final AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort;

    public PublishAvailabilitySearchService(
            AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort
    ) {
        this.availabilitySearchEventPublisherPort = availabilitySearchEventPublisherPort;
    }

    @Override
    public void publish(AvailabilitySearch availabilitySearch) {
        availabilitySearchEventPublisherPort.publish(availabilitySearch);
    }
}

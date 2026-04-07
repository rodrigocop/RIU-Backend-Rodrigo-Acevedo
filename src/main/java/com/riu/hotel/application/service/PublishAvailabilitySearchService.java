package com.riu.hotel.application.service;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.application.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.domain.port.out.AvailabilitySearchEventPublisherPort;

public class PublishAvailabilitySearchService implements PublishAvailabilitySearchUseCase {

    private final AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort;

    public PublishAvailabilitySearchService(
            AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort
    ) {
        this.availabilitySearchEventPublisherPort = availabilitySearchEventPublisherPort;
    }

    @Override
    public void execute(AvailabilitySearch availabilitySearch) {
        availabilitySearchEventPublisherPort.publish(availabilitySearch);
    }
}

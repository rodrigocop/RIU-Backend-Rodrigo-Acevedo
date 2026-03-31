package com.riu.hotel.domain.port.out;

import com.riu.hotel.domain.model.AvailabilitySearch;

public interface AvailabilitySearchEventPublisherPort {

    void publish(AvailabilitySearch availabilitySearch);
}

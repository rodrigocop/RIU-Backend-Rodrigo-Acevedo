package com.riu.hotel.port.out;

import com.riu.hotel.domain.model.AvailabilitySearch;

public interface AvailabilitySearchEventPublisherPort {

    void publish(AvailabilitySearch availabilitySearch);
}

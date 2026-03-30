package com.riu.hotel.port.in;

import com.riu.hotel.domain.model.AvailabilitySearch;

public interface PublishAvailabilitySearchUseCase {

    void publish(AvailabilitySearch availabilitySearch);
}

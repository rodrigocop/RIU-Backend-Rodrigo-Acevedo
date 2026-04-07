package com.riu.hotel.application.port.in;

import com.riu.hotel.domain.model.AvailabilitySearch;

public interface PublishAvailabilitySearchUseCase {

    void execute(AvailabilitySearch availabilitySearch);
}

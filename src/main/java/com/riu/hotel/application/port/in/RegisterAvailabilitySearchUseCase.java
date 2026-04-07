package com.riu.hotel.application.port.in;

import com.riu.hotel.domain.model.AvailabilitySearch;

public interface RegisterAvailabilitySearchUseCase {

    void execute(AvailabilitySearch availabilitySearch);
}

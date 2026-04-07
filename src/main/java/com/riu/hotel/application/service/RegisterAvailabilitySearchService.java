package com.riu.hotel.application.service;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.application.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.domain.port.out.AvailabilitySearchPersistencePort;

public class RegisterAvailabilitySearchService implements RegisterAvailabilitySearchUseCase {

    private final AvailabilitySearchPersistencePort availabilitySearchPersistencePort;

    public RegisterAvailabilitySearchService(
            AvailabilitySearchPersistencePort availabilitySearchPersistencePort
    ) {
        this.availabilitySearchPersistencePort = availabilitySearchPersistencePort;
    }

    @Override
    public void execute(AvailabilitySearch availabilitySearch) {
        availabilitySearchPersistencePort.save(availabilitySearch);
    }
}

package com.riu.hotel.application.service;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.port.out.AvailabilitySearchPersistencePort;

public class RegisterAvailabilitySearchService implements RegisterAvailabilitySearchUseCase {

    private final AvailabilitySearchPersistencePort availabilitySearchPersistencePort;

    public RegisterAvailabilitySearchService(
            AvailabilitySearchPersistencePort availabilitySearchPersistencePort
    ) {
        this.availabilitySearchPersistencePort = availabilitySearchPersistencePort;
    }

    @Override
    public void register(AvailabilitySearch availabilitySearch) {
        availabilitySearchPersistencePort.save(availabilitySearch);
    }
}

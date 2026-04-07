package com.riu.hotel.application.service;

import com.riu.hotel.application.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.domain.port.out.AvailabilitySearchQueryPort;
import java.util.Optional;

public class CountEqualSearchesService implements CountEqualSearchesUseCase {

    private final AvailabilitySearchQueryPort availabilitySearchQueryPort;

    public CountEqualSearchesService(AvailabilitySearchQueryPort availabilitySearchQueryPort) {
        this.availabilitySearchQueryPort = availabilitySearchQueryPort;
    }

    @Override
    public Optional<EqualSearchesResult> execute(String searchId) {
        return availabilitySearchQueryPort.findDetailWithEqualCount(searchId);
    }
}

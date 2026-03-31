package com.riu.hotel.application.service;

import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.domain.model.SearchCriteria;
import com.riu.hotel.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.port.out.AvailabilitySearchQueryPort;

import java.util.Optional;

public class CountEqualSearchesService implements CountEqualSearchesUseCase {

    private final AvailabilitySearchQueryPort availabilitySearchQueryPort;

    public CountEqualSearchesService(AvailabilitySearchQueryPort availabilitySearchQueryPort) {
        this.availabilitySearchQueryPort = availabilitySearchQueryPort;
    }

    @Override
    public Optional<EqualSearchesResult> getSearchDetails(String searchId) {
        Optional<SearchCriteria> criteriaOpt = availabilitySearchQueryPort.findBySearchId(searchId);
        if (criteriaOpt.isEmpty()) {
            return Optional.empty();
        }
        SearchCriteria criteria = criteriaOpt.get();
        long count = availabilitySearchQueryPort.countByCriteria(criteria);
        EqualSearchesResult result = EqualSearchesResult
                .builder()
                .searchId(searchId)
                .hotelId(criteria.getHotelId())
                .checkIn(criteria.getCheckIn())
                .checkOut(criteria.getCheckOut())
                .ages(criteria.getAges())
                .count(count)
                .build();
        return Optional.of(result);
    }
}

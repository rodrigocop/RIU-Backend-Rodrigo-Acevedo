package com.riu.hotel.infrastructure.out.persistence;

import com.riu.hotel.domain.model.SearchCriteria;
import com.riu.hotel.domain.port.out.AvailabilitySearchQueryPort;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvailabilitySearchQueryAdapter implements AvailabilitySearchQueryPort {

    private final AvailabilitySearchRepository repository;

    public AvailabilitySearchQueryAdapter(AvailabilitySearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<SearchCriteria> findBySearchId(String searchId) {
        return repository.findById(searchId).map(this::toCriteria);
    }

    @Override
    public long countByCriteria(SearchCriteria criteria) {
        return repository.countByHotelIdAndCheckInDateAndCheckOutDateAndAgesHash(
                criteria.getHotelId(),
                criteria.getCheckIn(),
                criteria.getCheckOut(),
                criteria.getAgeHash()
        );
    }

    private SearchCriteria toCriteria(AvailabilitySearchEntity entity) {
        return SearchCriteria.builder()
                .hotelId(entity.getHotelId())
                .checkIn(entity.getCheckInDate())
                .checkOut(entity.getCheckOutDate())
                .ages(Arrays.stream(entity.getAges().split(","))
                        .map(String::trim)
                        .map(Integer::valueOf)
                        .toList())
                .ageHash(entity.getAgesHash())
                .build();
    }
}

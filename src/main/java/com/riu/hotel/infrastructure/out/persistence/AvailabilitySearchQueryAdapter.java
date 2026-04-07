package com.riu.hotel.infrastructure.out.persistence;

import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.domain.port.out.AvailabilitySearchQueryPort;
import java.util.Arrays;
import java.util.List;
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
    public Optional<EqualSearchesResult> findDetailWithEqualCount(String searchId) {
        return repository.findWithDuplicateCount(searchId)
                .map(result -> {
                    AvailabilitySearchEntity entity = result.entity();
                    return new EqualSearchesResult(
                            entity.getId(),
                            entity.getHotelId(),
                            entity.getCheckInDate(),
                            entity.getCheckOutDate(),
                            parseAges(entity.getAges()),
                            result.count()
                    );
                });
    }

    private static List<Integer> parseAges(String agesCsv) {
        return Arrays.stream(agesCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();
    }
}

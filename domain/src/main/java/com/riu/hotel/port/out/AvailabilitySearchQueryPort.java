package com.riu.hotel.port.out;

import com.riu.hotel.domain.model.SearchCriteria;
import java.util.Optional;

public interface AvailabilitySearchQueryPort {

    Optional<SearchCriteria> findBySearchId(String searchId);

    long countByCriteria(SearchCriteria criteria);
}

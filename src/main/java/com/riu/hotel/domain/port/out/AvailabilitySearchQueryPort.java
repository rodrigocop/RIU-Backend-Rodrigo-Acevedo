package com.riu.hotel.domain.port.out;

import com.riu.hotel.domain.model.EqualSearchesResult;
import java.util.Optional;

public interface AvailabilitySearchQueryPort {

    Optional<EqualSearchesResult> findDetailWithEqualCount(String searchId);
}

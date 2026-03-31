package com.riu.hotel.port.in;

import com.riu.hotel.domain.model.EqualSearchesResult;
import java.util.Optional;

public interface CountEqualSearchesUseCase {

    Optional<EqualSearchesResult> getSearchDetails(String searchId);
}

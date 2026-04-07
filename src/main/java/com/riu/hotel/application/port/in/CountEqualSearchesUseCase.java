package com.riu.hotel.application.port.in;

import com.riu.hotel.domain.model.EqualSearchesResult;
import java.util.Optional;

public interface CountEqualSearchesUseCase {

    Optional<EqualSearchesResult> execute(String searchId);
}

package com.riu.hotel.infrastructure.out.persistence.dto;

import com.riu.hotel.infrastructure.out.persistence.AvailabilitySearchEntity;

public record SearchMatchDTO(
        AvailabilitySearchEntity entity,
        long count
) {}

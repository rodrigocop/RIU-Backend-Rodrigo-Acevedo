package com.riu.hotel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AvailabilitySearch(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        LocalDateTime requestedAt
) {

    public AvailabilitySearch {
        Objects.requireNonNull(searchId, "searchId");
        Objects.requireNonNull(hotelId, "hotelId");
        Objects.requireNonNull(checkIn, "checkIn");
        Objects.requireNonNull(checkOut, "checkOut");
        Objects.requireNonNull(ages, "ages");
        Objects.requireNonNull(requestedAt, "requestedAt");
        ages = List.copyOf(ages);
    }
}

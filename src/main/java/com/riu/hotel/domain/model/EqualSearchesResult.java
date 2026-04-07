package com.riu.hotel.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record EqualSearchesResult(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        long count
) {

    public EqualSearchesResult {
        Objects.requireNonNull(searchId, "searchId");
        Objects.requireNonNull(hotelId, "hotelId");
        Objects.requireNonNull(checkIn, "checkIn");
        Objects.requireNonNull(checkOut, "checkOut");
        Objects.requireNonNull(ages, "ages");
        ages = List.copyOf(ages);
    }
}

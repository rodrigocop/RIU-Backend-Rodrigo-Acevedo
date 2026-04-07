package com.riu.hotel.domain.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class EqualSearchesResult {

    String searchId;
    String hotelId;
    LocalDate checkIn;
    LocalDate checkOut;
    List<Integer> ages;
    long count;
}

package com.riu.hotel.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
@Value
public class AvailabilitySearch {

    String searchId;

    String hotelId;

    LocalDate checkIn;

    LocalDate checkOut;

    List<Integer> ages;

    LocalDateTime requestedAt;
}

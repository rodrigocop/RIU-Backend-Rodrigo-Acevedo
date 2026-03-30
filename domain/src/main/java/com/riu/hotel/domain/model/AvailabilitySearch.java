package com.riu.hotel.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

@Builder
@Data
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilitySearch {

    String searchId;

    String hotelId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkIn;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkOut;

    List<Integer> ages;

    LocalDateTime requestedAt;
}

package com.riu.hotel.domain.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EqualSearchesResult {

    String searchId;
    String hotelId;
    LocalDate checkIn;
    LocalDate checkOut;
    List<Integer> ages;
    long count;
}

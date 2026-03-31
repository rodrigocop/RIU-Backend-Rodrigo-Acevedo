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
public class SearchCriteria {

    String hotelId;
    LocalDate checkIn;
    LocalDate checkOut;
    List<Integer> ages;
    String ageHash;
}

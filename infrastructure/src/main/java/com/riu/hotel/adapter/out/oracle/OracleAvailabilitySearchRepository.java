package com.riu.hotel.adapter.out.oracle;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleAvailabilitySearchRepository extends JpaRepository<AvailabilitySearchEntity, String> {

    long countByHotelIdAndCheckInDateAndCheckOutDateAndAgesHash(
            String hotelId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            String agesHash
    );
}

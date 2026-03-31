package com.riu.hotel.infrastructure.out.persistence;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilitySearchRepository extends JpaRepository<AvailabilitySearchEntity, String> {

    long countByHotelIdAndCheckInDateAndCheckOutDateAndAgesHash(
            String hotelId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            String agesHash
    );
}

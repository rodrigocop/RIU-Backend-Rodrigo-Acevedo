package com.riu.hotel.infrastructure.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOTEL_AVAILABILITY_SEARCHES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySearchEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "hotel_id", nullable = false, length = 50)
    private String hotelId;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "ages", nullable = false, length = 4000)
    private String ages;

    @Column(name = "ages_hash", nullable = false, length = 1000)
    private String agesHash;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
}

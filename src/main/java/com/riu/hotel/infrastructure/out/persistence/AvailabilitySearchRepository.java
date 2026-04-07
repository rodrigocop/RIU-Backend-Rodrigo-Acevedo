package com.riu.hotel.infrastructure.out.persistence;

import java.util.Optional;

import com.riu.hotel.infrastructure.out.persistence.dto.SearchMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilitySearchRepository extends JpaRepository<AvailabilitySearchEntity, String> {

    @Query("""
            select new com.riu.hotel.infrastructure.out.persistence.dto.SearchMatchDTO(e,
                   (select count(e2)
                     from AvailabilitySearchEntity e2
                     where e2.hotelId = e.hotelId
                       and e2.checkInDate = e.checkInDate
                       and e2.checkOutDate = e.checkOutDate
                       and e2.agesHash = e.agesHash)
                   )
            from AvailabilitySearchEntity e
           where e.id = :id
          """)
    Optional<SearchMatchDTO> findWithDuplicateCount(@Param("id") String id);
}

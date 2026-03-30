package com.riu.hotel.adapter.out.oracle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleAvailabilitySearchRepository extends JpaRepository<AvailabilitySearchEntity, Long> {
}

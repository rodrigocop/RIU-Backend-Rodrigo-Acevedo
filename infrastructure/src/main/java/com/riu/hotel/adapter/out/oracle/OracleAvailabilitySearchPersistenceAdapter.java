package com.riu.hotel.adapter.out.oracle;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.out.AvailabilitySearchPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OracleAvailabilitySearchPersistenceAdapter implements AvailabilitySearchPersistencePort {

    private final OracleAvailabilitySearchRepository oracleAvailabilitySearchRepository;

    public OracleAvailabilitySearchPersistenceAdapter(
            OracleAvailabilitySearchRepository oracleAvailabilitySearchRepository
    ) {
        this.oracleAvailabilitySearchRepository = oracleAvailabilitySearchRepository;
    }

    @Override
    public void save(AvailabilitySearch availabilitySearch) {
        try {
            AvailabilitySearchEntity entity = AvailabilitySearchEntity.builder()
                 //   .id(availabilitySearch.getSearchId())
                    .hotelId(availabilitySearch.getHotelId())
                    .checkInDate(availabilitySearch.getCheckIn())
                    .checkOutDate(availabilitySearch.getCheckOut())
                    .agesJson(availabilitySearch.getAges().toString())
                    .requestedAt(availabilitySearch.getRequestedAt())
                    .build();

            oracleAvailabilitySearchRepository.save(entity);
            log.info(
                    "Búsqueda persistida: searchId={}, checkIn={}, checkOut={}, número de edades={}",
                  //  availabilitySearch.getSearchId(),
                    availabilitySearch.getCheckIn(),
                    availabilitySearch.getCheckOut(),
                    availabilitySearch.getAges().size());
        } catch (Exception e) {
            log.error("No se pudo serializar ages para Oracle; searchId={}", availabilitySearch.getHotelId(), e);
            throw new IllegalStateException("Error al serializar ages para persistencia", e);
        }
    }
}

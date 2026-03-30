package com.riu.hotel.adapter.out.oracle;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.out.AvailabilitySearchPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
                    .checkInDate(availabilitySearch.getCheckIn())
                    .checkOutDate(availabilitySearch.getCheckOut())
                    .agesJson(availabilitySearch.getAges().toString())
                    .requestedAt(availabilitySearch.getRequestedAt())
                    .hotelId(availabilitySearch.getHotelId())
                    .id(UUID.randomUUID().toString())
                    .build();

            oracleAvailabilitySearchRepository.save(entity);
            log.info(
                    "Búsqueda de disponibilidad persistida (checkIn={}, checkOut={}, edades registradas={})",
                    availabilitySearch.getCheckIn(),
                    availabilitySearch.getCheckOut(),
                    availabilitySearch.getAges().size()
            );
        } catch (Exception e) {
            log.error("No se pudo serializar el listado de edades para Oracle", e);
            throw new IllegalStateException("Error al serializar ages para persistencia", e);
        }
    }
}

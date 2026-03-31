package com.riu.hotel.adapter.out.oracle;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.out.AvailabilitySearchPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
            String ages = availabilitySearch.getAges().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            String ageHash = String.valueOf(ages.hashCode());

            AvailabilitySearchEntity entity = AvailabilitySearchEntity.builder()
                    .id(availabilitySearch.getSearchId())
                    .hotelId(availabilitySearch.getHotelId())
                    .checkInDate(availabilitySearch.getCheckIn())
                    .checkOutDate(availabilitySearch.getCheckOut())
                    .ages(ages)
                    .agesHash(ageHash)
                    .requestedAt(availabilitySearch.getRequestedAt())
                    .build();

            oracleAvailabilitySearchRepository.save(entity);
            log.info(
                    "Búsqueda persistida: searchId={}, checkIn={}, checkOut={}, número de edades={}",
                    availabilitySearch.getSearchId(),
                    availabilitySearch.getCheckIn(),
                    availabilitySearch.getCheckOut(),
                    availabilitySearch.getAges().size());
        } catch (Exception e) {
            log.error("No se pudo guardar la entidad con el searchId={}", availabilitySearch.getHotelId(), e);
            throw new IllegalStateException("Error al serializar ages para persistencia", e);
        }
    }
}

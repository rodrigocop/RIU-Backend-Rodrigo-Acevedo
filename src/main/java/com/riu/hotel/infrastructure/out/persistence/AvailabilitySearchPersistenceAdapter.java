package com.riu.hotel.infrastructure.out.persistence;

import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.domain.port.out.AvailabilitySearchPersistencePort;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvailabilitySearchPersistenceAdapter implements AvailabilitySearchPersistencePort {

    private final AvailabilitySearchRepository availabilitySearchRepository;

    public AvailabilitySearchPersistenceAdapter(AvailabilitySearchRepository availabilitySearchRepository) {
        this.availabilitySearchRepository = availabilitySearchRepository;
    }

    @Override
    public void save(AvailabilitySearch availabilitySearch) {
        try {
            String ages = availabilitySearch.ages().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            String ageHash = String.valueOf(ages.hashCode());

            AvailabilitySearchEntity entity = AvailabilitySearchEntity.builder()
                    .id(availabilitySearch.searchId())
                    .hotelId(availabilitySearch.hotelId())
                    .checkInDate(availabilitySearch.checkIn())
                    .checkOutDate(availabilitySearch.checkOut())
                    .ages(ages)
                    .agesHash(ageHash)
                    .requestedAt(availabilitySearch.requestedAt())
                    .build();

            availabilitySearchRepository.save(entity);
            log.info(
                    "Búsqueda persistida: searchId={}, checkIn={}, checkOut={}, número de edades={}",
                    availabilitySearch.searchId(),
                    availabilitySearch.checkIn(),
                    availabilitySearch.checkOut(),
                    availabilitySearch.ages().size());
        } catch (Exception e) {
            log.error("No se pudo guardar la entidad con el searchId={}", availabilitySearch.searchId(), e);
            throw new IllegalStateException("Error al persistir búsqueda", e);
        }
    }
}

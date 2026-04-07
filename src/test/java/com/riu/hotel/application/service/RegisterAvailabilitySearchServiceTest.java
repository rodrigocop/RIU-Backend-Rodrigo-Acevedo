package com.riu.hotel.application.service;

import static com.riu.hotel.HotelTestFactory.sampleAvailabilitySearch;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.riu.hotel.domain.port.out.AvailabilitySearchPersistencePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterAvailabilitySearchServiceTest {

    @Mock
    private AvailabilitySearchPersistencePort availabilitySearchPersistencePort;

    @InjectMocks
    private RegisterAvailabilitySearchService registerAvailabilitySearchService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(availabilitySearchPersistencePort);
    }

    @Test
    void shouldDelegatePersistence() {
        var search = sampleAvailabilitySearch();

        assertAll(
                () -> registerAvailabilitySearchService.execute(search),
                () -> verify(availabilitySearchPersistencePort).save(search));
    }
}

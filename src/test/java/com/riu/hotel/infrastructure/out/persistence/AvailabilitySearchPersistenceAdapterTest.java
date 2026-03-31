package com.riu.hotel.infrastructure.out.persistence;

import static com.riu.hotel.testsupport.HotelTestFixtures.AGES;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_IN;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_OUT;
import static com.riu.hotel.testsupport.HotelTestFixtures.HOTEL_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.SEARCH_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.REQUESTED_AT;
import static com.riu.hotel.testsupport.HotelTestFixtures.agesCsv;
import static com.riu.hotel.testsupport.HotelTestFixtures.agesHash;
import static com.riu.hotel.testsupport.HotelTestFixtures.sampleAvailabilitySearch;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.riu.hotel.domain.model.AvailabilitySearch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchPersistenceAdapterTest {

    @Mock
    private AvailabilitySearchRepository availabilitySearchRepository;

    @InjectMocks
    private AvailabilitySearchPersistenceAdapter adapter;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(availabilitySearchRepository);
    }

    @Test
    void shouldMapAndPersistEntity() {
        AvailabilitySearch search = sampleAvailabilitySearch();

        adapter.save(search);

        var captor = ArgumentCaptor.forClass(AvailabilitySearchEntity.class);
        assertAll(
                () -> verify(availabilitySearchRepository).save(captor.capture()),
                () -> {
                    AvailabilitySearchEntity e = captor.getValue();
                    assertAll(
                            () -> assertEquals(SEARCH_ID, e.getId()),
                            () -> assertEquals(HOTEL_ID, e.getHotelId()),
                            () -> assertEquals(CHECK_IN, e.getCheckInDate()),
                            () -> assertEquals(CHECK_OUT, e.getCheckOutDate()),
                            () -> assertEquals(agesCsv(AGES), e.getAges()),
                            () -> assertEquals(agesHash(AGES), e.getAgesHash()),
                            () -> assertEquals(REQUESTED_AT, e.getRequestedAt()));
                });
    }

    @Test
    void shouldWrapPersistenceFailure() {
        doThrow(new RuntimeException("db")).when(availabilitySearchRepository).save(any());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> adapter.save(sampleAvailabilitySearch()));

        assertAll(
                () -> assertEquals("Error al persistir búsqueda", ex.getMessage()),
                () -> assertEquals("db", ex.getCause().getMessage()),
                () -> verify(availabilitySearchRepository).save(any()));
    }
}

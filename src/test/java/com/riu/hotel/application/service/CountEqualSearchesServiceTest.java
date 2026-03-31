package com.riu.hotel.application.service;

import static com.riu.hotel.testsupport.HotelTestFixtures.AGES;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_IN;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_OUT;
import static com.riu.hotel.testsupport.HotelTestFixtures.EQUAL_COUNT;
import static com.riu.hotel.testsupport.HotelTestFixtures.HOTEL_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.SEARCH_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.sampleSearchCriteria;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.riu.hotel.domain.port.out.AvailabilitySearchQueryPort;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountEqualSearchesServiceTest {

    @Mock
    private AvailabilitySearchQueryPort availabilitySearchQueryPort;

    @InjectMocks
    private CountEqualSearchesService countEqualSearchesService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(availabilitySearchQueryPort);
    }

    @Test
    void shouldReturnEmptyWhenSearchIdUnknown() {
        when(availabilitySearchQueryPort.findBySearchId(SEARCH_ID)).thenReturn(Optional.empty());

        Optional<?> result = countEqualSearchesService.execute(SEARCH_ID);

        assertAll(
                () -> assertTrue(result.isEmpty(), "no debe haber resultado si no existe el searchId"),
                () -> verify(availabilitySearchQueryPort).findBySearchId(SEARCH_ID));
    }

    @Test
    void shouldReturnCountAndSnapshotWhenSearchExists() {
        var criteria = sampleSearchCriteria();
        when(availabilitySearchQueryPort.findBySearchId(SEARCH_ID)).thenReturn(Optional.of(criteria));
        when(availabilitySearchQueryPort.countByCriteria(criteria)).thenReturn(EQUAL_COUNT);

        var result = countEqualSearchesService.execute(SEARCH_ID).orElseThrow();

        assertAll(
                () -> assertEquals(SEARCH_ID, result.getSearchId()),
                () -> assertEquals(HOTEL_ID, result.getHotelId()),
                () -> assertEquals(CHECK_IN, result.getCheckIn()),
                () -> assertEquals(CHECK_OUT, result.getCheckOut()),
                () -> assertEquals(AGES, result.getAges()),
                () -> assertEquals(EQUAL_COUNT, result.getCount()),
                () -> verify(availabilitySearchQueryPort).findBySearchId(SEARCH_ID),
                () -> verify(availabilitySearchQueryPort).countByCriteria(criteria));
    }
}

package com.riu.hotel.application.service;

import static com.riu.hotel.HotelTestFactory.AGES;
import static com.riu.hotel.HotelTestFactory.CHECK_IN;
import static com.riu.hotel.HotelTestFactory.CHECK_OUT;
import static com.riu.hotel.HotelTestFactory.EQUAL_COUNT;
import static com.riu.hotel.HotelTestFactory.HOTEL_ID;
import static com.riu.hotel.HotelTestFactory.SEARCH_ID;
import static com.riu.hotel.HotelTestFactory.sampleEqualSearchesResult;
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
        when(availabilitySearchQueryPort.findDetailWithEqualCount(SEARCH_ID)).thenReturn(Optional.empty());

        Optional<?> result = countEqualSearchesService.execute(SEARCH_ID);

        assertAll(
                () -> assertTrue(result.isEmpty(), "no debe haber resultado si no existe el searchId"),
                () -> verify(availabilitySearchQueryPort).findDetailWithEqualCount(SEARCH_ID));
    }

    @Test
    void shouldReturnCountAndSnapshotWhenSearchExists() {
        when(availabilitySearchQueryPort.findDetailWithEqualCount(SEARCH_ID))
                .thenReturn(Optional.of(sampleEqualSearchesResult()));

        var result = countEqualSearchesService.execute(SEARCH_ID).orElseThrow();

        assertAll(
                () -> assertEquals(SEARCH_ID, result.searchId()),
                () -> assertEquals(HOTEL_ID, result.hotelId()),
                () -> assertEquals(CHECK_IN, result.checkIn()),
                () -> assertEquals(CHECK_OUT, result.checkOut()),
                () -> assertEquals(AGES, result.ages()),
                () -> assertEquals(EQUAL_COUNT, result.count()),
                () -> verify(availabilitySearchQueryPort).findDetailWithEqualCount(SEARCH_ID));
    }
}

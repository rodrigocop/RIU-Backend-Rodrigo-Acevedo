package com.riu.hotel.infrastructure.out.persistence;

import static com.riu.hotel.HotelTestFactory.AGES;
import static com.riu.hotel.HotelTestFactory.CHECK_IN;
import static com.riu.hotel.HotelTestFactory.CHECK_OUT;
import static com.riu.hotel.HotelTestFactory.EQUAL_COUNT;
import static com.riu.hotel.HotelTestFactory.HOTEL_ID;
import static com.riu.hotel.HotelTestFactory.SEARCH_ID;
import static com.riu.hotel.HotelTestFactory.sampleEntity;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.infrastructure.out.persistence.dto.SearchMatchDTO;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvailabilitySearchQueryAdapterTest {

    @Mock
    private AvailabilitySearchRepository repository;

    @InjectMocks
    private AvailabilitySearchQueryAdapter adapter;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnEmptyWhenIdMissing() {
        when(repository.findWithDuplicateCount(SEARCH_ID)).thenReturn(Optional.empty());

        Optional<EqualSearchesResult> found = adapter.findDetailWithEqualCount(SEARCH_ID);

        assertAll(
                () -> assertTrue(found.isEmpty()),
                () -> verify(repository).findWithDuplicateCount(SEARCH_ID));
    }

    @Test
    void shouldMapRowToEqualSearchesResult() {
        when(repository.findWithDuplicateCount(SEARCH_ID))
                .thenReturn(Optional.of(new SearchMatchDTO(sampleEntity(), EQUAL_COUNT)));

        var result = adapter.findDetailWithEqualCount(SEARCH_ID).orElseThrow();

        assertAll(
                () -> assertEquals(SEARCH_ID, result.searchId()),
                () -> assertEquals(HOTEL_ID, result.hotelId()),
                () -> assertEquals(CHECK_IN, result.checkIn()),
                () -> assertEquals(CHECK_OUT, result.checkOut()),
                () -> assertEquals(AGES, result.ages()),
                () -> assertEquals(EQUAL_COUNT, result.count()),
                () -> verify(repository).findWithDuplicateCount(SEARCH_ID));
    }
}

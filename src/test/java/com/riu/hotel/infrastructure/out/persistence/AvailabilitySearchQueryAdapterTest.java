package com.riu.hotel.infrastructure.out.persistence;

import static com.riu.hotel.testsupport.HotelTestFixtures.AGES;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_IN;
import static com.riu.hotel.testsupport.HotelTestFixtures.CHECK_OUT;
import static com.riu.hotel.testsupport.HotelTestFixtures.EQUAL_COUNT;
import static com.riu.hotel.testsupport.HotelTestFixtures.HOTEL_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.SEARCH_ID;
import static com.riu.hotel.testsupport.HotelTestFixtures.agesHash;
import static com.riu.hotel.testsupport.HotelTestFixtures.sampleEntity;
import static com.riu.hotel.testsupport.HotelTestFixtures.sampleSearchCriteria;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
        when(repository.findById(SEARCH_ID)).thenReturn(Optional.empty());

        Optional<?> found = adapter.findBySearchId(SEARCH_ID);

        assertAll(
                () -> assertTrue(found.isEmpty()),
                () -> verify(repository).findById(SEARCH_ID));
    }

    @Test
    void shouldMapEntityToCriteria() {
        when(repository.findById(SEARCH_ID)).thenReturn(Optional.of(sampleEntity()));

        var criteria = adapter.findBySearchId(SEARCH_ID).orElseThrow();

        assertAll(
                () -> assertEquals(HOTEL_ID, criteria.getHotelId()),
                () -> assertEquals(CHECK_IN, criteria.getCheckIn()),
                () -> assertEquals(CHECK_OUT, criteria.getCheckOut()),
                () -> assertEquals(AGES, criteria.getAges()),
                () -> assertEquals(agesHash(AGES), criteria.getAgeHash()),
                () -> verify(repository).findById(SEARCH_ID));
    }

    @Test
    void shouldDelegateCountByCriteria() {
        var criteria = sampleSearchCriteria();
        when(repository.countByHotelIdAndCheckInDateAndCheckOutDateAndAgesHash(
                HOTEL_ID, CHECK_IN, CHECK_OUT, agesHash(AGES))).thenReturn(EQUAL_COUNT);

        long count = adapter.countByCriteria(criteria);

        assertAll(
                () -> assertEquals(EQUAL_COUNT, count),
                () -> verify(repository).countByHotelIdAndCheckInDateAndCheckOutDateAndAgesHash(
                        HOTEL_ID, CHECK_IN, CHECK_OUT, agesHash(AGES)));
    }
}

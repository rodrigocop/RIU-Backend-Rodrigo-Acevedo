package com.riu.hotel.application.service;

import static com.riu.hotel.testsupport.HotelTestFixtures.sampleAvailabilitySearch;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.riu.hotel.domain.port.out.AvailabilitySearchEventPublisherPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PublishAvailabilitySearchServiceTest {

    @Mock
    private AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort;

    @InjectMocks
    private PublishAvailabilitySearchService publishAvailabilitySearchService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(availabilitySearchEventPublisherPort);
    }

    @Test
    void shouldDelegatePublish() {
        var search = sampleAvailabilitySearch();

        assertAll(
                () -> publishAvailabilitySearchService.execute(search),
                () -> verify(availabilitySearchEventPublisherPort).publish(search));
    }
}

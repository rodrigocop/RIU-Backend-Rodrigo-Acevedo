package com.riu.hotel.infrastructure.out.kafka;

import static com.riu.hotel.testsupport.HotelTestFixtures.sampleAvailabilitySearch;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.riu.hotel.domain.model.AvailabilitySearch;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@ExtendWith(MockitoExtension.class)
class KafkaAvailabilitySearchProducerAdapterTest {

    private static final String TOPIC = "hotel_availability_searches";

    @Mock
    private KafkaTemplate<String, AvailabilitySearch> kafkaTemplate;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(kafkaTemplate);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void shouldSendKeyedMessageToTopic() throws Exception {
        @SuppressWarnings("unchecked")
        SendResult<String, AvailabilitySearch> sendResult = mock(SendResult.class);
        when(kafkaTemplate.send(eq(TOPIC), eq(sampleAvailabilitySearch().getSearchId()), eq(sampleAvailabilitySearch())))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        var adapter = new KafkaAvailabilitySearchProducerAdapter(kafkaTemplate, TOPIC);
        AvailabilitySearch search = sampleAvailabilitySearch();

        assertAll(
                () -> adapter.publish(search),
                () -> verify(kafkaTemplate).send(TOPIC, search.getSearchId(), search));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void shouldSwallowSendFailure() {
        when(kafkaTemplate.send(eq(TOPIC), eq(sampleAvailabilitySearch().getSearchId()), eq(sampleAvailabilitySearch())))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("broker")));

        var adapter = new KafkaAvailabilitySearchProducerAdapter(kafkaTemplate, TOPIC);
        AvailabilitySearch search = sampleAvailabilitySearch();

        assertAll(
                () -> adapter.publish(search),
                () -> verify(kafkaTemplate).send(TOPIC, search.getSearchId(), search));
    }
}

package com.riu.hotel.infrastructure.in.kafka;

import static com.riu.hotel.testsupport.HotelTestFixtures.sampleAvailabilitySearch;
import static com.riu.hotel.testsupport.HotelTestFixtures.availabilitySearchAsJson;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.domain.port.in.RegisterAvailabilitySearchUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaAvailabilitySearchConsumerAdapterTest {

    @Mock
    private RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(registerAvailabilitySearchUseCase, objectMapper);
    }

    @Test
    void shouldDeserializeWithRealMapperAndRegister() throws Exception {
        ObjectMapper realMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var adapter = new KafkaAvailabilitySearchConsumerAdapter(registerAvailabilitySearchUseCase, realMapper);
        AvailabilitySearch search = sampleAvailabilitySearch();
        String json = availabilitySearchAsJson(search);

        adapter.consume(json);

        assertAll(
                () -> verify(registerAvailabilitySearchUseCase).execute(search));
    }

    @Test
    void shouldNotRegisterWhenJsonInvalid() throws Exception {
        var adapter = new KafkaAvailabilitySearchConsumerAdapter(registerAvailabilitySearchUseCase, objectMapper);
        when(objectMapper.readValue(anyString(), eq(AvailabilitySearch.class)))
                .thenThrow(JsonProcessingException.class);

        assertAll(
                () -> adapter.consume("{no-es-json}"),
                () -> verify(registerAvailabilitySearchUseCase, never()).execute(any()),
                () -> verify(objectMapper).readValue(anyString(), eq(AvailabilitySearch.class)));
    }
}

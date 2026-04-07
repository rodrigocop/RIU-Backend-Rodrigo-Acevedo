package com.riu.hotel.infrastructure.in.kafka;

import static com.riu.hotel.HotelTestFactory.availabilitySearchAsJson;
import static com.riu.hotel.HotelTestFactory.sampleAvailabilitySearch;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.riu.hotel.application.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.domain.model.AvailabilitySearch;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaAvailabilitySearchConsumerAdapterTest {

    private static final Executor DIRECT = Runnable::run;

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
        var adapter = new KafkaAvailabilitySearchConsumerAdapter(
                registerAvailabilitySearchUseCase, realMapper, DIRECT);
        AvailabilitySearch search = sampleAvailabilitySearch();
        String json = availabilitySearchAsJson(search);

        adapter.consume(json);

        verify(registerAvailabilitySearchUseCase).execute(search);
    }

    @Test
    void shouldNotRegisterWhenJsonInvalid() throws Exception {
        var adapter = new KafkaAvailabilitySearchConsumerAdapter(
                registerAvailabilitySearchUseCase, objectMapper, DIRECT);
        when(objectMapper.readValue(anyString(), eq(AvailabilitySearch.class)))
                .thenThrow(JsonProcessingException.class);

        adapter.consume("{no-es-un-json-valido}");

        assertAll(
                () -> verify(registerAvailabilitySearchUseCase, never()).execute(any()),
                () -> verify(objectMapper).readValue(anyString(), eq(AvailabilitySearch.class)));
    }

    @Test
    void shouldPropagateWhenPersistenceFailsAfterValidJson() throws Exception {
        ObjectMapper realMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var adapter = new KafkaAvailabilitySearchConsumerAdapter(
                registerAvailabilitySearchUseCase, realMapper, DIRECT);
        AvailabilitySearch search = sampleAvailabilitySearch();
        String json = availabilitySearchAsJson(search);
        doThrow(new IllegalStateException("fallo BD")).when(registerAvailabilitySearchUseCase).execute(search);

        assertThrows(IllegalStateException.class, () -> adapter.consume(json));

        verify(registerAvailabilitySearchUseCase).execute(search);
    }
}

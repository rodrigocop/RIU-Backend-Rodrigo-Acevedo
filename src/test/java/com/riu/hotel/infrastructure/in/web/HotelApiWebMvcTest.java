package com.riu.hotel.infrastructure.in.web;

import static com.riu.hotel.HotelTestFactory.AGES;
import static com.riu.hotel.HotelTestFactory.CHECK_IN;
import static com.riu.hotel.HotelTestFactory.CHECK_OUT;
import static com.riu.hotel.HotelTestFactory.EQUAL_COUNT;
import static com.riu.hotel.HotelTestFactory.HOTEL_ID;
import static com.riu.hotel.HotelTestFactory.SEARCH_ID;
import static com.riu.hotel.HotelTestFactory.sampleEqualSearchesResult;
import static com.riu.hotel.HotelTestFactory.sampleSearchPostBody;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.riu.hotel.application.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.application.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.infrastructure.in.web.error.ApiExceptionHandler;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AvailabilitySearchController.class, GetSearchController.class})
@Import(ApiExceptionHandler.class)
@ActiveProfiles("test")
class HotelApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublishAvailabilitySearchUseCase publishAvailabilitySearchUseCase;

    @MockBean
    private CountEqualSearchesUseCase countEqualSearchesUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(publishAvailabilitySearchUseCase, countEqualSearchesUseCase);
    }

    @Test
    void shouldAcceptSearchAndPublishEvent() throws Exception {
        mockMvc.perform(post("/api/v1/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleSearchPostBody()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.searchId").isString())
                .andExpect(jsonPath("$.searchId").isNotEmpty());

        assertAll(
                () -> verify(publishAvailabilitySearchUseCase).execute(argThat(s ->
                        HOTEL_ID.equals(s.hotelId())
                                && CHECK_IN.equals(s.checkIn())
                                && CHECK_OUT.equals(s.checkOut())
                                && AGES.equals(s.ages())
                                && s.searchId() != null
                                && s.requestedAt() != null)));
    }

    @Test
    void shouldRejectInvalidSearchPayload() throws Exception {
        mockMvc.perform(post("/api/v1/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.errores").isArray())
                .andExpect(jsonPath("$.errores.length()").value(greaterThan(0)));
    }

    @Test
    void shouldReturnCountWhenSearchExists() throws Exception {
        when(countEqualSearchesUseCase.execute(SEARCH_ID)).thenReturn(Optional.of(sampleEqualSearchesResult()));

        mockMvc.perform(get("/api/v1/count/{searchId}", SEARCH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(SEARCH_ID))
                .andExpect(jsonPath("$.count").value(EQUAL_COUNT))
                .andExpect(jsonPath("$.search.hotelId").value(HOTEL_ID));

        verify(countEqualSearchesUseCase).execute(SEARCH_ID);
    }

    @Test
    void shouldReturn404WhenSearchMissing() throws Exception {
        when(countEqualSearchesUseCase.execute(SEARCH_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/count/{searchId}", SEARCH_ID))
                .andExpect(status().isNotFound());

        verify(countEqualSearchesUseCase).execute(SEARCH_ID);
    }
}

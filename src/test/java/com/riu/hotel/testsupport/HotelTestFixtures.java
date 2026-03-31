package com.riu.hotel.testsupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.domain.model.SearchCriteria;
import com.riu.hotel.infrastructure.out.persistence.AvailabilitySearchEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Objetos de prueba compartidos (caso feliz y errores). Evita duplicar literales entre tests.
 */
public final class HotelTestFixtures {

    public static final String SEARCH_ID = "a1b2c3d4-e5f6-7890-abcd-ef1234567890";
    public static final String HOTEL_ID = "1234aBc";
    public static final LocalDate CHECK_IN = LocalDate.of(2023, 12, 29);
    public static final LocalDate CHECK_OUT = LocalDate.of(2023, 12, 31);
    public static final List<Integer> AGES = List.of(30, 29, 1, 3);
    public static final LocalDateTime REQUESTED_AT = LocalDateTime.of(2023, 12, 28, 12, 0, 0);
    public static final long EQUAL_COUNT = 42L;

    private static final ObjectMapper API_JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private HotelTestFixtures() {
    }

    public static String agesCsv(List<Integer> ages) {
        return ages.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static String agesHash(List<Integer> ages) {
        return String.valueOf(agesCsv(ages).hashCode());
    }

    public static AvailabilitySearch sampleAvailabilitySearch() {
        return AvailabilitySearch.builder()
                .searchId(SEARCH_ID)
                .hotelId(HOTEL_ID)
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .ages(AGES)
                .requestedAt(REQUESTED_AT)
                .build();
    }

    public static SearchCriteria sampleSearchCriteria() {
        return SearchCriteria.builder()
                .hotelId(HOTEL_ID)
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .ages(AGES)
                .ageHash(agesHash(AGES))
                .build();
    }

    public static AvailabilitySearchEntity sampleEntity() {
        return AvailabilitySearchEntity.builder()
                .id(SEARCH_ID)
                .hotelId(HOTEL_ID)
                .checkInDate(CHECK_IN)
                .checkOutDate(CHECK_OUT)
                .ages(agesCsv(AGES))
                .agesHash(agesHash(AGES))
                .requestedAt(REQUESTED_AT)
                .build();
    }

    public static EqualSearchesResult sampleEqualSearchesResult() {
        return EqualSearchesResult.builder()
                .searchId(SEARCH_ID)
                .hotelId(HOTEL_ID)
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .ages(AGES)
                .count(EQUAL_COUNT)
                .build();
    }

    /**
     * JSON válido para POST /search (fechas dd/MM/yyyy como en la API).
     */
    public static String sampleSearchPostBody() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return """
                {
                  "hotelId": "%s",
                  "checkIn": "%s",
                  "checkOut": "%s",
                  "ages": [30, 29, 1, 3]
                }
                """
                .formatted(HOTEL_ID, CHECK_IN.format(fmt), CHECK_OUT.format(fmt));
    }

    public static String availabilitySearchAsJson(AvailabilitySearch search) throws JsonProcessingException {
        return API_JSON_MAPPER.writeValueAsString(search);
    }
}

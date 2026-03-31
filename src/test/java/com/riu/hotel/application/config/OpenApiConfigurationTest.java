package com.riu.hotel.application.config;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class OpenApiConfigurationTest {

    @Test
    void shouldExposeApiMetadata() {
        var openApi = new OpenApiConfiguration().hotelOpenApi();

        assertAll(
                () -> assertNotNull(openApi.getInfo()),
                () -> assertEquals("API Hotel", openApi.getInfo().getTitle()),
                () -> assertEquals("0.0.1-SNAPSHOT", openApi.getInfo().getVersion()),
                () -> assertNotNull(openApi.getInfo().getDescription()));
    }
}

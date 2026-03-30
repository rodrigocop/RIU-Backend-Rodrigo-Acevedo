package com.riu.hotel.bootstrap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI hotelOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Hotel")
                        .description("Servicios REST del backend Hotel. Las búsquedas de disponibilidad se publican en Kafka para su procesamiento.")
                        .version("0.0.1-SNAPSHOT"));
    }
}

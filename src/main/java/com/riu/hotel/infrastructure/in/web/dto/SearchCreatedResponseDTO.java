package com.riu.hotel.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Schema(description = "Respuesta de crear la búsqueda")
@Builder
@Value
@Jacksonized
public class SearchCreatedResponseDTO {

    @Schema(
            description = "Identificador único de la búsqueda",
            example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
    )
    String searchId;
}

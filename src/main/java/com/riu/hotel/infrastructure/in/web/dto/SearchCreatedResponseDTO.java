package com.riu.hotel.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de crear la búsqueda")
public record SearchCreatedResponseDTO(
        @Schema(
                description = "Identificador único de la búsqueda",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        )
        String searchId
) {
}

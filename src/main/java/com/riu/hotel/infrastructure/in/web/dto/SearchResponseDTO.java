package com.riu.hotel.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchResponseDTO(
        @Schema(example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        String searchId,

        @Schema(description = "Criterios de la búsqueda de referencia")
        SearchSnapshotResponseDTO search,

        @Schema(description = "Número de registros con los mismos criterios", example = "100")
        long count
) {
}

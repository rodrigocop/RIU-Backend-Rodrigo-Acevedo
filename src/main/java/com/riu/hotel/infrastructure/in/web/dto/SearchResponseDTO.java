package com.riu.hotel.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResponseDTO {

    @Schema(example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private String searchId;

    @Schema(description = "Criterios de la búsqueda de referencia")
    private SearchSnapshotResponse search;

    @Schema(description = "Número de registros con los mismos criterios", example = "100")
    private long count;
}

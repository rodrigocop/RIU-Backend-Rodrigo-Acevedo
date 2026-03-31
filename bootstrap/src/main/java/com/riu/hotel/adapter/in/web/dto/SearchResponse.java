package com.riu.hotel.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResponse {
    @Schema(example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    String searchId;
    @Schema(description = "Criterios de la búsqueda de referencia")
    SearchSnapshotResponse search;
    @Schema(description = "Número de registros con hotelId, fechas y ages idénticos", example = "100")
    long count;
}

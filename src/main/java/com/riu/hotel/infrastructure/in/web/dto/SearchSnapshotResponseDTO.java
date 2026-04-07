package com.riu.hotel.infrastructure.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Datos de la búsqueda de referencia")
public record SearchSnapshotResponseDTO(
        @Schema(example = "1234aBc")
        String hotelId,

        @Schema(example = "29/12/2023")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @Schema(example = "31/12/2023")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @Schema(example = "[3, 29, 30, 1]")
        List<Integer> ages
) {
}

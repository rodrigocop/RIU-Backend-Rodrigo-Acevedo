package com.riu.hotel.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Datos de la búsqueda de referencia (mismos criterios usados para el conteo)")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSnapshotResponse {
    @Schema(example = "1234aBc")
    String hotelId;
    @Schema(example = "29/12/2023")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkIn;
    @Schema(example = "31/12/2023")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkOut;
    @Schema(example = "[3, 29, 30, 1]")
    List<Integer> ages;

}

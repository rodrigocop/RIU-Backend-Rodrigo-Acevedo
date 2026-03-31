package com.riu.hotel.infrastructure.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos de la búsqueda de referencia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSnapshotResponse {

    @Schema(example = "1234aBc")
    private String hotelId;

    @Schema(example = "29/12/2023")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @Schema(example = "31/12/2023")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    @Schema(example = "[3, 29, 30, 1]")
    private List<Integer> ages;
}

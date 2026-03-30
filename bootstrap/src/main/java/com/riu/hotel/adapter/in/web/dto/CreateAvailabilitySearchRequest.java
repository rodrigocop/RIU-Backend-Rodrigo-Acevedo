package com.riu.hotel.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "CreateAvailabilitySearchRequest", description = "Soliticud de reserva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAvailabilitySearchRequest {

    @Schema(description = "Codigo del hotel", example = "1234aBc")
    @NotBlank
    private String hotelId;

    @Schema(description = "Fecha de entrada (dd/MM/yyyy)", example = "29/12/2023")
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @Schema(description = "Fecha de salida (dd/MM/yyyy)", example = "31/12/2023")
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    @Schema(description = "Edades de los huespedes", example = "[30, 29, 1, 3]")
    @NotEmpty
    private List<@NotNull @Min(0) @Max(120) Integer> ages;

    @AssertTrue(message = "La fecha de salida debe ser posterior o igual a la de entrada")
    public boolean isValidDateRange() {
        if (checkIn == null || checkOut == null) {
            return true;
        }
        return !checkOut.isBefore(checkIn);
    }
}

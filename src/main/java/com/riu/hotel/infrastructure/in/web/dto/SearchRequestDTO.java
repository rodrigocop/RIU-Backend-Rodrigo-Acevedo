package com.riu.hotel.infrastructure.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "CreateAvailabilitySearchRequest", description = "Solicitud de reserva")
@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchRequestDTO(
        @NotBlank(message = "El hotelId es obligatorio")
        @Size(max = 50, message = "El hotelId no puede superar 50 caracteres")
        String hotelId,

        @NotNull(message = "La fecha de entrada (checkIn) es obligatoria")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @NotNull(message = "La fecha de salida (checkOut) es obligatoria")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @NotEmpty(message = "Debe indicar al menos una edad en el arreglo ages")
        @Valid
        List<
                        @NotNull(message = "Cada elemento de ages debe ser un número (no null)")
                        @Min(value = 0, message = "La edad no puede ser menor que {value}")
                        @Max(value = 120, message = "La edad no puede ser mayor que {value}")
                        Integer>
                ages
) {

    @AssertTrue(message = "La fecha de salida debe ser posterior o igual a la de entrada")
    @JsonIgnore
    public boolean isValidDateRange() {
        if (checkIn == null || checkOut == null) {
            return true;
        }
        return !checkOut.isBefore(checkIn);
    }
}

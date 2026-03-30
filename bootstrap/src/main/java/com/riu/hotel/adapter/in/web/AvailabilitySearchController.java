package com.riu.hotel.adapter.in.web;

import com.riu.hotel.adapter.in.web.dto.CreateAvailabilitySearchRequest;
import com.riu.hotel.adapter.in.web.error.ValidationErrorResponse;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.port.in.PublishAvailabilitySearchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Disponibilidad hotel", description = "Registro de búsquedas de disponibilidad")
public class AvailabilitySearchController {

    private final PublishAvailabilitySearchUseCase publishAvailabilitySearchUseCase;

    public AvailabilitySearchController(PublishAvailabilitySearchUseCase publishAvailabilitySearchUseCase) {
        this.publishAvailabilitySearchUseCase = publishAvailabilitySearchUseCase;
    }

    @Operation(
            summary = "Registrar búsqueda de disponibilidad",
            description = "Registrar búsqueda de disponibilidad"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Solicitud aceptada para procesamiento"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Respuesta con los errores en caso de no cumplir con el contrato",
                     content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Solicitud de disponibilidad",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = CreateAvailabilitySearchRequest.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "Ejemplo request",
                            value = """
                                    {
                                      "hotelId": "1234aBc",
                                      "checkIn": "29/12/2023",
                                      "checkOut": "31/12/2023",
                                      "ages": [30, 29, 1, 3]
                                    }
                                    """
                    )
            )
    )
    public void createAvailabilitySearch(@Valid @RequestBody CreateAvailabilitySearchRequest request) {
        AvailabilitySearch availabilitySearch = AvailabilitySearch.builder()
                .hotelId(request.getHotelId())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .ages(List.copyOf(request.getAges()))
                .requestedAt(LocalDateTime.now())
                .build();

        publishAvailabilitySearchUseCase.publish(availabilitySearch);
        log.info("Solicitud de disponibilidad recibida, hotelId de trazabilidad {}", request.getHotelId());
    }
}

package com.riu.hotel.infrastructure.in.web;

import com.riu.hotel.application.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.domain.model.AvailabilitySearch;
import com.riu.hotel.infrastructure.in.web.dto.SearchCreatedResponseDTO;
import com.riu.hotel.infrastructure.in.web.dto.SearchRequestDTO;
import com.riu.hotel.infrastructure.in.web.error.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
            description = "Genera un searchId, encola el evento en Kafka y responde de inmediato; el mismo searchId se persiste al procesar el mensaje."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Aceptada para procesamiento",
                    content = @Content(schema = @Schema(implementation = SearchCreatedResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errores de validación",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Solicitud de disponibilidad",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = SearchRequestDTO.class),
                    examples =
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Ejemplo request",
                                    value =
                                            """
                                            {
                                              "hotelId": "1234aBc",
                                              "checkIn": "29/12/2023",
                                              "checkOut": "31/12/2023",
                                              "ages": [30, 29, 1, 3]
                                            }
                                            """)))
    public ResponseEntity<SearchCreatedResponseDTO> createAvailabilitySearch(
            @Valid @RequestBody SearchRequestDTO request) {

        String searchId = UUID.randomUUID().toString();

        AvailabilitySearch availabilitySearch = AvailabilitySearch.builder()
                .searchId(searchId)
                .hotelId(request.getHotelId())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .ages(List.copyOf(request.getAges()))
                .requestedAt(LocalDateTime.now())
                .build();

        publishAvailabilitySearchUseCase.execute(availabilitySearch);
        log.info(
                "Búsqueda encolada: searchId={}, hotelId de trazabilidad {}",
                searchId,
                request.getAges());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SearchCreatedResponseDTO.builder().searchId(searchId).build());
    }
}

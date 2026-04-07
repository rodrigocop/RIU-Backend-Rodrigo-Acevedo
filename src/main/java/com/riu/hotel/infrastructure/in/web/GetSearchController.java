package com.riu.hotel.infrastructure.in.web;

import com.riu.hotel.application.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.infrastructure.in.web.dto.SearchResponseDTO;
import com.riu.hotel.infrastructure.in.web.dto.SearchSnapshotResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/count/{searchId}")
@Tag(name = "Conteo de búsquedas", description = "Consulta cuántas búsquedas coinciden con los criterios del searchId dado")
public class GetSearchController {

    private final CountEqualSearchesUseCase countEqualSearchesUseCase;

    public GetSearchController(CountEqualSearchesUseCase countEqualSearchesUseCase) {
        this.countEqualSearchesUseCase = countEqualSearchesUseCase;
    }

    @Operation(summary = "Datos de búsqueda y total de coincidencias", description = "Devuelve la búsqueda asociada al searchId y el número de filas equivalentes.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda encontrada",
                    content = @Content(schema = @Schema(implementation = SearchResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningún registro con ese searchId")
    })
    @GetMapping
    public ResponseEntity<SearchResponseDTO> getSearchDetails(
            @PathVariable(name = "searchId")
            @Parameter(description = "ID de la búsqueda a consultar", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            String searchId) {

        return countEqualSearchesUseCase
                .execute(searchId)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Registro no encontrado para searchId: {}", searchId);
                    return ResponseEntity.notFound().build();
                });
    }

    private SearchResponseDTO toResponse(EqualSearchesResult result) {
        SearchSnapshotResponse snapshot = SearchSnapshotResponse
                .builder()
                .hotelId(result.getHotelId())
                .checkIn(result.getCheckIn())
                .checkOut(result.getCheckOut())
                .ages(result.getAges())
                .build();
        return SearchResponseDTO.builder()
                .searchId(result.getSearchId())
                .search(snapshot)
                .count(result.getCount())
                .build();
    }
}

package com.riu.hotel.adapter.in.web;

import com.riu.hotel.adapter.in.web.dto.SearchResponse;
import com.riu.hotel.adapter.in.web.dto.SearchSnapshotResponse;
import com.riu.hotel.domain.model.EqualSearchesResult;
import com.riu.hotel.port.in.CountEqualSearchesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/count")
public class GetSearchController {

    private final CountEqualSearchesUseCase countEqualSearchesUseCase;

    public GetSearchController(CountEqualSearchesUseCase countEqualSearchesUseCase) {
        this.countEqualSearchesUseCase = countEqualSearchesUseCase;
    }

    @Operation(summary = "Devuelve los datos de una reserva", description = "Devuelve los datos de una busqueda segun el searchId y agrega el total de coincidencias")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda encontrada",
                    content = @Content(schema = @Schema(implementation = SearchResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe ningún registro con ese searchId")
    })
    @GetMapping(value = "/{searchId}")
    public ResponseEntity<SearchResponse> getSearchDetails(
            @PathVariable(name = "searchId") @Parameter(description = "ID de la búsqueda a consultar", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
            String searchId) {

        return countEqualSearchesUseCase
                .getSearchDetails(searchId)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Registro no encontrado para searchId: {}", searchId);
                    return ResponseEntity.notFound().build();
                });

    }

    private SearchResponse toResponse(EqualSearchesResult result) {
        SearchSnapshotResponse snapshot = SearchSnapshotResponse
                .builder()
                .hotelId(result.getHotelId())
                .checkIn(result.getCheckIn())
                .checkOut(result.getCheckOut())
                .ages(result.getAges())
                .build();
        return SearchResponse.builder()
                .searchId(result.getSearchId())
                .count(result.getCount())
                .search(snapshot)
                .build();
    }
}

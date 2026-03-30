package com.riu.hotel.adapter.in.web.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "Error de validación sobre la petición")
@Builder
public record ValidationErrorResponse(
        @Schema(description = "Resumen del error", example = "Existen errores en los datos enviados")
        String mensaje,
        List<String> errores) { }



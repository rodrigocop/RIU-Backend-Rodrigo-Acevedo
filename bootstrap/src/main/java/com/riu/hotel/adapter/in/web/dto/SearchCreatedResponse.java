package com.riu.hotel.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta de crear la busqueda")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCreatedResponse{
        @Schema(
                description = "Identificador único de la búsqueda (se genera al aceptar la petición y coincide con el registro en base de datos)",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        )
        private String searchId;


}

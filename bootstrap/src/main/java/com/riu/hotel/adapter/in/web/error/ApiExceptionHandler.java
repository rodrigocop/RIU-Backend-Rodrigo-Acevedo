package com.riu.hotel.adapter.in.web.error;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String MENSAJE_RESUMEN_VALIDACION = "Existen errores en los datos enviados";
    private static final String MENSAJE_PAYLOAD_ILEGIBLE = "El cuerpo de la petición no es válido; revise el JSON y use fechas en formato dd/MM/yyyy";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest()
                .body(ValidationErrorResponse
                        .builder()
                        .mensaje(MENSAJE_RESUMEN_VALIDACION)
                        .errores(errores)
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidBody(HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest()
                .body(ValidationErrorResponse
                        .builder()
                        .mensaje(MENSAJE_PAYLOAD_ILEGIBLE)
                        .errores(List.of(ex.getMessage()))
                        .build());
    }
}

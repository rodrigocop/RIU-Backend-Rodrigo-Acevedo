package com.riu.hotel.infrastructure.in.web.error;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.riu.hotel.infrastructure.in.web.AvailabilitySearchController;
import com.riu.hotel.infrastructure.in.web.dto.SearchRequestDTO;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void shouldMapValidationErrorsToBadRequest() throws Exception {
        var target = SearchRequestDTO.builder().build();
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(target, "createAvailabilitySearchRequest");
        bindingResult.addError(new FieldError(
                "createAvailabilitySearchRequest",
                "hotelId",
                "",
                false,
                new String[] {"NotBlank"},
                null,
                "El hotelId es obligatorio"));
        Method controllerMethod = AvailabilitySearchController.class.getDeclaredMethod(
                "createAvailabilitySearch", SearchRequestDTO.class);
        MethodParameter parameter = new MethodParameter(controllerMethod, 0);
        var ex = new MethodArgumentNotValidException(parameter, bindingResult);

        var response = handler.handleValidation(ex);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assertNotNull(response.getBody());
                    assertEquals(
                            "Existen errores en los datos enviados",
                            response.getBody().getMensaje());
                },
                () -> {
                    assertNotNull(response.getBody());
                    List<String> errors = response.getBody().getErrores();
                    assertAll(
                            () -> assertEquals(1, errors.size()),
                            () -> assertTrue(errors.getFirst().contains("hotelId")));
                });
    }

    @Test
    void shouldMapUnreadableBodyToBadRequest() {
        var ex = new HttpMessageNotReadableException("parse error");

        var response = handler.handleInvalidBody(ex);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assertNotNull(response.getBody());
                    assertTrue(response.getBody().getMensaje().contains("no es válido"));
                },
                () -> {
                    assertNotNull(response.getBody());
                    assertEquals(1, response.getBody().getErrores().size());
                });
    }
}

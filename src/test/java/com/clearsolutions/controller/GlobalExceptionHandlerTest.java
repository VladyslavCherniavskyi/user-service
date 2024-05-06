package com.clearsolutions.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.stream.Stream;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    private record Exceptions(Exception exception, HttpStatus httpStatus) {
    }

    @Test
    void handleException() {
        //given
        Stream.of(
                new Exceptions(new EntityNotFoundException("Entity not found"), HttpStatus.NOT_FOUND),
                new Exceptions(new ConstraintViolationException("Constraint violation", null), HttpStatus.BAD_REQUEST),
                new Exceptions(new ValidationException("Validation failed"), HttpStatus.BAD_REQUEST),
                new Exceptions(new RuntimeException("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR)
        ).forEach(exceptionData -> {
                    var exception = exceptionData.exception();
                    var expectedStatus = exceptionData.httpStatus();

                    //when
                    var response = handler.handleException(exception);

                    //then
                    Assertions.assertEquals(expectedStatus, response.getStatusCode());
                    Assertions.assertEquals(expectedStatus.value(), Objects.requireNonNull(response.getBody()).get("status"));
                    Assertions.assertEquals(exception.getMessage(), response.getBody().get("message"));
                }
        );
    }

}
package com.maersk.availabiltyservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AvailabilityServiceExceptionHandler {

    @ExceptionHandler(value = {InvalidContainerSizeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public Map<String, String> handleInValidFormatExceptions(
            InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put(ex.getValue().toString(), ex.getMessage());
        return errors;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleRuntimeException(
            Exception ex) {
        Map<String, String> errors = new HashMap<>();
        ex.printStackTrace();
        errors.put("error", "Internal Server Error");
        return errors;
    }
}

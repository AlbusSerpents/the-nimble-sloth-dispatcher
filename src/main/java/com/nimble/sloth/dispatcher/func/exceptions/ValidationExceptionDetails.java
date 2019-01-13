package com.nimble.sloth.dispatcher.func.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class ValidationExceptionDetails {
    private final String field;
    private final String message;

    public static ValidationExceptionDetails fromFieldError(final FieldError error) {
        return new ValidationExceptionDetails(error.getField(), error.getDefaultMessage());
    }

    public String toMessage() {
        return field + ": " + message;
    }
}

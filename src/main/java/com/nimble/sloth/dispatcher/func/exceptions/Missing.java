package com.nimble.sloth.dispatcher.func.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class Missing extends CustomException {
    private static final String MESSAGE_TEMPLATE = "Resource %s is missing";

    public Missing(final String identifier) {
        super(toMessage(identifier));
    }

    private static String toMessage(
            final String identifier) {
        return String.format(MESSAGE_TEMPLATE, identifier);
    }
}

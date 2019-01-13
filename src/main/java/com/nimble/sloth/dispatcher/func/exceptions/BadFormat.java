package com.nimble.sloth.dispatcher.func.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class BadFormat extends CustomException {

    private static final String DESERIALIZE_MESSAGE = "Can't parse %s";
    private static final String RESPONSE_MESSAGE = "Corrupt data";

    public BadFormat(final String json) {
        super(RESPONSE_MESSAGE, toMessage(json));
    }

    public BadFormat(final Exception e) {
        super(RESPONSE_MESSAGE, e);
    }

    private static String toMessage(final String json) {
        return String.format(DESERIALIZE_MESSAGE, json);
    }

}

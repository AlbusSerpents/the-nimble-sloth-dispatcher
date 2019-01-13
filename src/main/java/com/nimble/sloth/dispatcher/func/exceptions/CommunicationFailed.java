package com.nimble.sloth.dispatcher.func.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class CommunicationFailed extends CustomException {

    private static final String RESPONSE_MESSAGE = "Communication Problem";

    public CommunicationFailed(final Exception cause) {
        super(RESPONSE_MESSAGE, cause);
    }

    public CommunicationFailed(final String message) {
        super(RESPONSE_MESSAGE, message);
    }
}

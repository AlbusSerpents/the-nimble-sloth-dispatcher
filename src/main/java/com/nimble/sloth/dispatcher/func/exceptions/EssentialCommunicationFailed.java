package com.nimble.sloth.dispatcher.func.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ResponseStatus(SERVICE_UNAVAILABLE)
public class EssentialCommunicationFailed extends CustomException {

    private static final String MESSAGE = "An unrecoverable error in the communication has occurred. Server is unable to process requests";

    public EssentialCommunicationFailed(final Exception cause) {
        super(MESSAGE, cause);
    }

    public EssentialCommunicationFailed(final String cause) {
        super(cause, MESSAGE);
    }
}

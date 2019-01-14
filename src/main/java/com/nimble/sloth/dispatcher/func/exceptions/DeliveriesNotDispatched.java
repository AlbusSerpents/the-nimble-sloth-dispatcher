package com.nimble.sloth.dispatcher.func.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class DeliveriesNotDispatched extends CustomException {
    private static final String MESSAGE = "Couldn't send deliveries via the queue";

    public DeliveriesNotDispatched() {
        super(MESSAGE);
    }
}

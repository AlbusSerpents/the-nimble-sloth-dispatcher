package com.nimble.sloth.dispatcher.func.exceptions;

import com.nimble.sloth.dispatcher.func.properties.PropertiesKey;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(PRECONDITION_FAILED)
public class MissingConfiguration extends CustomException {

    private static final String MESSAGE = "Missing system property %s";

    public MissingConfiguration(final PropertiesKey key) {
        super(String.format(MESSAGE, key.name()));
    }
}

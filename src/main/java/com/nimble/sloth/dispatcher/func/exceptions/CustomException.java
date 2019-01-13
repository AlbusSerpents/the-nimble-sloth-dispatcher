package com.nimble.sloth.dispatcher.func.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class CustomException extends RuntimeException {
    private final String responseMessage;

    CustomException(final String responseMessage) {
        super();
        this.responseMessage = responseMessage;
    }

    CustomException(final String responseMessage, final String message) {
        super(message);
        this.responseMessage = responseMessage;
    }

    CustomException(final String responseMessage, final Throwable cause) {
        super(cause);
        this.responseMessage = responseMessage;
    }

    @JsonProperty(access = READ_ONLY, value = "error")
    public String getResponseMessage() {
        return responseMessage;
    }
}

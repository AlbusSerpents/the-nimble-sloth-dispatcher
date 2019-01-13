package com.nimble.sloth.dispatcher.rest;

import com.nimble.sloth.dispatcher.func.queue.QueueMessage;
import com.nimble.sloth.dispatcher.func.queue.QueueMessageResponse;
import com.nimble.sloth.dispatcher.func.queue.QueueService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/queue")
public class QueueTestEndpoint {

    private final QueueService service;

    public QueueTestEndpoint(final QueueService service) {
        this.service = service;
    }

    @RequestMapping(value = "/poll", method = GET)
    public String pollFromQueue() {
        return service.receive();
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/push", method = POST)
    public QueueMessageResponse putInQueue(final @RequestBody @Valid QueueMessage message) {
        return service.send(message);
    }
}

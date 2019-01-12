package com.nimble.sloth.dispatcher.rest;

import com.nimble.sloth.dispatcher.func.status.ApplicationStatus;
import com.nimble.sloth.dispatcher.func.test.TestObject;
import com.nimble.sloth.dispatcher.func.test.TestService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("")
public class StatusEndpoint {

    private final TestService service;

    public StatusEndpoint(final TestService service) {
        this.service = service;
    }

    @RequestMapping(value = "/status", method = GET)
    public ApplicationStatus status() {
        return new ApplicationStatus("The Nimble Sloth dispatcher", "localhost:8080", "OK");
    }

    @RequestMapping(value = "/{name}", method = GET)
    public TestObject testDatabase(final @PathVariable("name") String name) {
        return service.getObject(name);
    }
}

package com.nimble.sloth.dispatcher.func.test;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final TestRepository repository;

    public TestService(final TestRepository repository) {
        this.repository = repository;
    }

    public TestObject getObject(final String name) {
        return repository.findByName(name);
    }
}

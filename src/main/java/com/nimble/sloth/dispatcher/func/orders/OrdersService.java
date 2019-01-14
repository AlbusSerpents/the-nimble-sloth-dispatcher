package com.nimble.sloth.dispatcher.func.orders;

import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    private final OrdersRepository repository;

    public OrdersService(final OrdersRepository repository) {
        this.repository = repository;
    }

    public OrderAcceptedResponse create(final Order order) {
        final boolean saved = repository.save(order);
        return new OrderAcceptedResponse(saved);
    }
}
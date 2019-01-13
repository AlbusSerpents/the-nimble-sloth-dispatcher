package com.nimble.sloth.dispatcher.func.orders;

import com.nimble.sloth.dispatcher.func.exceptions.Missing;
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

    public Order findById(final String orderId) {
        final String errorMessage = String.format("Order with id %s doesn't exist", orderId);
        return repository
                .findById(orderId)
                .orElseThrow(() -> new Missing(errorMessage));
    }
}
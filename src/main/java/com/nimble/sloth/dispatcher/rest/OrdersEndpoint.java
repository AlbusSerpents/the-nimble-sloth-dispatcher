package com.nimble.sloth.dispatcher.rest;

import com.nimble.sloth.dispatcher.func.orders.Order;
import com.nimble.sloth.dispatcher.func.orders.OrderAcceptedResponse;
import com.nimble.sloth.dispatcher.func.orders.OrdersService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    private final OrdersService service;

    public OrdersEndpoint(final OrdersService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/add", method = POST)
    public OrderAcceptedResponse addOrder(final @RequestBody @Valid Order order) {
        return service.create(order);
    }
}

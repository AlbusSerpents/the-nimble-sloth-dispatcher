package com.nimble.sloth.dispatcher.func.orders.deliveries;

import com.nimble.sloth.dispatcher.func.orders.Order;
import com.nimble.sloth.dispatcher.func.orders.OrderLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class Delivery {
    private final String orderId;
    private final OrderLocation pickUp;
    private final OrderLocation destination;

    public static Delivery toWarehouse(
            final Order order,
            final OrderLocation warehouse) {
        final String orderId = order.getOrderId();
        final OrderLocation pickUp = order.getPickUp();
        return new Delivery(orderId, pickUp, warehouse);
    }

    public static Delivery fromWarehouse(
            final Order order,
            final OrderLocation warehouse) {
        final String orderId = order.getOrderId();
        final OrderLocation destination = order.getDestination();
        return new Delivery(orderId, warehouse, destination);
    }
}

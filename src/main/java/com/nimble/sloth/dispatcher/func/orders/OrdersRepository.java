package com.nimble.sloth.dispatcher.func.orders;

import java.util.Collection;

public interface OrdersRepository {

    boolean save(final Order order);

    Collection<Order> findWaitingForPickUp();

    Collection<Order> findByIds(final Collection<String> orderIds);

    boolean markAsSentForPickup(final String orderId);

    boolean markAsSentForDelivery(final String orderId);
}

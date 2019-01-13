package com.nimble.sloth.dispatcher.func.orders;

import java.util.Collection;
import java.util.Optional;

public interface OrdersRepository {

    boolean save(final Order order);

    Optional<Order> findById(final String orderId);

    Collection<Order> findByIds(final Collection<String> orderIds);

    boolean markAsSentForPickup(final String orderId);

    boolean markAsSentForDelivery(final String orderId);

    void removeByIds(final Collection<String> orderIds);
}

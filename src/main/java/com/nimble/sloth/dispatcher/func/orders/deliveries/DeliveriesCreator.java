package com.nimble.sloth.dispatcher.func.orders.deliveries;

import com.nimble.sloth.dispatcher.func.orders.Order;
import com.nimble.sloth.dispatcher.func.orders.OrderLocation;
import com.nimble.sloth.dispatcher.func.orders.OrdersRepository;
import com.nimble.sloth.dispatcher.func.orders.warehouse.WarehouseService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static com.nimble.sloth.dispatcher.func.orders.deliveries.Delivery.fromWarehouse;
import static com.nimble.sloth.dispatcher.func.orders.deliveries.Delivery.toWarehouse;
import static java.util.stream.Collectors.toList;

@Component
public class DeliveriesCreator {

    private final WarehouseService warehouseService;
    private final OrdersRepository ordersRepository;

    public DeliveriesCreator(
            final WarehouseService warehouseService,
            final OrdersRepository ordersRepository) {
        this.warehouseService = warehouseService;
        this.ordersRepository = ordersRepository;
    }

    public List<Delivery> fromWarehouseDeliveries(final OrderLocation warehouse) {
        final List<String> warehouseOrdersIds = warehouseService.getWarehouseContents();
        final Collection<Order> forDelivery = ordersRepository.findByIds(warehouseOrdersIds);
        return toDeliveries(forDelivery, order -> fromWarehouse(order, warehouse));
    }

    public List<Delivery> toWarehouseDeliveries(final OrderLocation warehouse) {
        final Collection<Order> forPickUp = ordersRepository.findWaitingForPickUp();
        return toDeliveries(forPickUp, order -> toWarehouse(order, warehouse));
    }

    private List<Delivery> toDeliveries(
            final Collection<Order> orders,
            final Function<Order, Delivery> deliveryFunction) {
        return orders
                .stream()
                .map(deliveryFunction)
                .collect(toList());
    }
}

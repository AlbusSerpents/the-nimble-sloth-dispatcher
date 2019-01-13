package com.nimble.sloth.dispatcher.func.orders;

import com.nimble.sloth.dispatcher.func.properties.PropertiesService;
import com.nimble.sloth.dispatcher.func.queue.QueueMessageResponse;
import com.nimble.sloth.dispatcher.func.queue.QueueService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.ORDERS_PER_TRUCK;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Service
public class OrdersService {
    private final QueueService queueService;
    private final PropertiesService propertiesService;

    public OrdersService(
            final QueueService queueService,
            final PropertiesService propertiesService) {
        this.queueService = queueService;
        this.propertiesService = propertiesService;
    }

    public OrderAcceptedResponse addOrder(final Order order) {
        final int ordersPerTruck = getOrdersPerTruck();
        final List<Order.Delivery> orders = range(0, ordersPerTruck)
                .mapToObj(i -> order)
                .map(this::toDelivery)
                .collect(toList());

        final Order.Delivery[] ordersArray = new Order.Delivery[orders.size()];
        orders.toArray(ordersArray);
        final QueueMessageResponse sent = queueService.send(orders);
        return new OrderAcceptedResponse(sent.isSuccess());
    }

    private Order.Delivery toDelivery(final Order order) {
        final String id = order.getOrderId();
        final Order.Location location = order.getPickUp();
        return new Order.Delivery(id, location);
    }

    private int getOrdersPerTruck() {
        final String value = propertiesService.getRequiredProperty(ORDERS_PER_TRUCK);
        return Integer.valueOf(value);
    }
}

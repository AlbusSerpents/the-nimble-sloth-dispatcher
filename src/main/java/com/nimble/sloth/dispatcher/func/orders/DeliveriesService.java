package com.nimble.sloth.dispatcher.func.orders;

import com.nimble.sloth.dispatcher.func.properties.PropertiesService;
import com.nimble.sloth.dispatcher.func.queue.QueueMessageResponse;
import com.nimble.sloth.dispatcher.func.queue.QueueService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.nimble.sloth.dispatcher.func.orders.Order.Delivery.toWarehouse;
import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.ORDERS_PER_TRUCK;
import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_LATITUDE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Service
public class DeliveriesService {

    private final QueueService queueService;
    private final PropertiesService propertiesService;

    public DeliveriesService(
            final QueueService queueService,
            final PropertiesService propertiesService) {
        this.queueService = queueService;
        this.propertiesService = propertiesService;
    }

    public OrderAcceptedResponse sendOrders(final Order order) {
        final int ordersPerTruck = getOrdersPerTruck();
        final Order.Location warehouse = warehouseLocation();
        final List<Order.Delivery> orders = range(0, ordersPerTruck)
                .mapToObj(i -> order)
                .map(newOrder -> toWarehouse(newOrder, warehouse))
                .collect(toList());

        final Order.Delivery[] ordersArray = new Order.Delivery[orders.size()];
        orders.toArray(ordersArray);
        final QueueMessageResponse sent = queueService.send(orders);
        return new OrderAcceptedResponse(sent.isSuccess());
    }

    private Order.Location warehouseLocation() {
        final String warehouseLatitude = propertiesService.getRequiredProperty(WAREHOUSE_LATITUDE);
        final String warehouseLongitude = propertiesService.getRequiredProperty(WAREHOUSE_LATITUDE);

        final BigDecimal latitude = new BigDecimal(warehouseLatitude);
        final BigDecimal longitude = new BigDecimal(warehouseLongitude);

        return new Order.Location(latitude, longitude);
    }

    private int getOrdersPerTruck() {
        final String value = propertiesService.getRequiredProperty(ORDERS_PER_TRUCK);
        return Integer.valueOf(value);
    }
}

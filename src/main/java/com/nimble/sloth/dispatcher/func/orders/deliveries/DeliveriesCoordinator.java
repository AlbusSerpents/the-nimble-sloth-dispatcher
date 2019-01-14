package com.nimble.sloth.dispatcher.func.orders.deliveries;

import com.nimble.sloth.dispatcher.func.properties.PropertiesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.ORDERS_PER_TRUCK;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Service
public class DeliveriesCoordinator {

    private final PropertiesService propertiesService;

    public DeliveriesCoordinator(final PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public List<List<Delivery>> groupDeliveries(final List<Delivery> deliveries) {
        final int maxGroupSize = getMaxGroupValue();

        final List<List<Delivery>> groups = range(0, maxGroupSize)
                .mapToObj(i -> new ArrayList<Delivery>())
                .collect(toList());

        for (int index = 0; index < deliveries.size(); index++) {
            final Delivery delivery = deliveries.get(index);
            final int groupIndex = index % maxGroupSize;
            final List<Delivery> group = groups.get(groupIndex);
            group.add(delivery);
        }

        return groups;
    }

    private int getMaxGroupValue() {
        final String value = propertiesService.getRequiredProperty(ORDERS_PER_TRUCK);
        return Integer.valueOf(value);
    }
}

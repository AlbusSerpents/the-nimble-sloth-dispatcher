package com.nimble.sloth.dispatcher.func.properties;

public enum PropertiesKey {
    WAREHOUSE_URL("url"),
    WAREHOUSE_TOKEN("token"),
    WAREHOUSE_LONGITUDE("longitude"),
    WAREHOUSE_LATITUDE("latitude"),
    QUEUE_NAME("queue-name"),
    QUEUE_TOPIC("queue-topic"),
    QUEUE_ROUTING("queue-routing"),
    ORDERS_PER_TRUCK("orders-per-truck");

    PropertiesKey(final String propertyName) {
        this.propertyName = propertyName;
    }

    private String propertyName;

    String getName() {
        return propertyName;
    }
}

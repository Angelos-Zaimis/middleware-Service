package com.middleware_service.middleware_service.configuration.kafka;

public class KafkaGroup {
    private KafkaGroup() { throw new IllegalStateException("Utility class"); }

    public static final String UPDATE_INVENTORY_GROUP = "updateInventoryGroup";
}

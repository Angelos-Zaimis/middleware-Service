package com.middleware_service.middleware_service.configuration.springdoc;

import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

public class SpringDocTags {
    public static final String ORDERS = "API for orders";
    public static final String ORDERS_DESCRIPTION = "API to manage orders";

    public static List<Tag> tags() {
        return List.of(
                new Tag().name(ORDERS).description(ORDERS_DESCRIPTION)
        );
    }
}

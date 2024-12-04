package com.middleware_service.middleware_service.configuration.springdoc;

import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

public class SpringDocTags {

    public static final String CREATE_AN_ORDER = "API for creating order";
    public static final String CREATE_AN_ORDER_DESCRIPTION = "API for creating an order";

    public static List<Tag> tags() {
        return List.of(
                new Tag().name(CREATE_AN_ORDER).description(CREATE_AN_ORDER_DESCRIPTION)
        );
    }
}

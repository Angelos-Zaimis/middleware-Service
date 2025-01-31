package com.middleware_service.middleware_service.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRxDTO {

    @NotNull(message = "User id must not be null")
    private UUID userId;

    @NotNull(message = "Product IDs list must not be null")
    @NotEmpty(message = "Product IDs list must not be empty")
    private List<@NotNull(message = "Product ID must not be null") UUID> productIds;
}

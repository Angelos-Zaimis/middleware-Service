package com.middleware_service.middleware_service.dto.orders;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRxDTO {

    @NotNull(message = "Product IDs list must not be null")
    @NotEmpty(message = "Product IDs list must not be empty")
    private List<@NotNull(message = "Product ID must not be null") UUID> productIds;
}

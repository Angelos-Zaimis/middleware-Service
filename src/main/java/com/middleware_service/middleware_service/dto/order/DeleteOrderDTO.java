package com.middleware_service.middleware_service.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderDTO {
    @NotNull(message = "Order id must not be null")
    private UUID orderId;

    @NotNull(message = "User id must not be null")
    private UUID userId;
}

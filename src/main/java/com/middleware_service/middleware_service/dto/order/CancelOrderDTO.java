package com.middleware_service.middleware_service.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderDTO {

    @NotNull(message = "Order id must not be null")
    private UUID orderId;

    @NotNull(message = "User id must not be null")
    private UUID userId;
}

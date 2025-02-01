package com.middleware_service.middleware_service.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "Product id must not be null")
    private UUID productId;

    @NotNull(message = "Quantity must not be null")
    private Integer quantity;
}

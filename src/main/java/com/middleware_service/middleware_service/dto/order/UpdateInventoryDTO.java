package com.middleware_service.middleware_service.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInventoryDTO {

    @NotNull(message = "Products must not be null")
    @NotEmpty(message = "Products must not be empty")
    private List<ProductDTO> products;

    @NotNull(message = "User Id must not be null")
    private UUID userId;
}

package com.middleware_service.middleware_service.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTxDTO {

    @NotNull(message = "Product id must not be null")
    private UUID id;

    @NotBlank(message = "Product name must not be empty")
    private String name;

    @NotNull(message = "Product price must not be null")
    private Double price;

    @NotNull(message = "Product sale price must not be null")
    private Double salePrice;

    @NotNull(message = "Product sku must not be null")
    private Integer sku;
}

package com.middleware_service.middleware_service.mapper;

import com.middleware_service.middleware_service.dto.product.ProductTxDTO;
import com.middleware_service.middleware_service.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "salePrice", source = "salePrice")
    @Mapping(target = "sku", source = "sku")
    OrderProduct map(ProductTxDTO productTxDTO);
}

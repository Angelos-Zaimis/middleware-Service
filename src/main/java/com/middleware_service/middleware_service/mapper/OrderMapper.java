package com.middleware_service.middleware_service.mapper;


import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "vat", ignore = true)
    @Mapping(target = "products", ignore = true)
    Order map(OrderRxDTO orderRxDTO);

    @Mapping(target = "productIds", ignore = true)
    OrderTxDTO mapToTxDTO(Order order);
}

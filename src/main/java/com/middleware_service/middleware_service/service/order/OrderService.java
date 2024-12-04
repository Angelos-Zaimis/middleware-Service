package com.middleware_service.middleware_service.service.order;

import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderTxDTO> retrieveOrders(UUID orderId, UUID userId);

    void createOrder(OrderRxDTO orderRxDTO);

    void cancelOrder(UUID orderId);
}

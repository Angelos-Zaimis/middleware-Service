package com.middleware_service.middleware_service.service.order;

import com.middleware_service.middleware_service.dto.orders.OrderRxDTO;

public interface OrderService {

    void createOrder(OrderRxDTO orderRxDTO);
}

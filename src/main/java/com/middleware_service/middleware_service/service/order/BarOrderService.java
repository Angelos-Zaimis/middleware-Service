package com.middleware_service.middleware_service.service.order;

import com.middleware_service.middleware_service.dto.orders.OrderRxDTO;
import com.middleware_service.middleware_service.exception.OrderException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarOrderService {

    private final OrderService orderService;

    public void handleCreateOrder(OrderRxDTO orderRxDTO) throws OrderException {
        try {
            orderService.createOrder(orderRxDTO);
        } catch (Exception e) {
            throw new OrderException("Error creating order", e);
        }
    }
}


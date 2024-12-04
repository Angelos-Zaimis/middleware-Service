package com.middleware_service.middleware_service.service.order;

import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BarOrderService {

    private final OrderService orderService;

    public List<OrderTxDTO> handleRetrieveOrders(UUID orderId, UUID userId) throws OrderException {
        try {
            return orderService.retrieveOrders(orderId, userId);
        } catch (Exception e) {
            throw new OrderException("Error retrieving orders", e);
        }
    }

    public void handleCreateOrder(OrderRxDTO orderRxDTO) throws OrderException {
        try {
            orderService.createOrder(orderRxDTO);
        } catch (Exception e) {
            throw new OrderException("Error creating order", e);
        }
    }

    public void handleCancelOrder(UUID orderId) throws OrderException {
        try {
            orderService.cancelOrder(orderId);
        } catch (Exception e) {
            throw new OrderException("Error canceling order", e);
        }
    }
}


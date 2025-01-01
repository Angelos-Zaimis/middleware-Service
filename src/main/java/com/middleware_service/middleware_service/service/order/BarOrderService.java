package com.middleware_service.middleware_service.service.order;

import com.middleware_service.middleware_service.dto.order.CancelOrderDTO;
import com.middleware_service.middleware_service.dto.order.DeleteOrderDTO;
import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BarOrderService {

    private final OrderService orderService;

    public List<OrderTxDTO> handleRetrieveOrders(UUID orderId, UUID userId) {
        return orderService.retrieveOrders(orderId, userId);
    }

    public void handleCreateOrder(OrderRxDTO orderRxDTO) {
        orderService.createOrder(orderRxDTO);
    }

    public void handleCancelOrder(CancelOrderDTO cancelOrderDTO) {
        orderService.cancelOrder(cancelOrderDTO);
    }

    public void handleDeleteOrder(DeleteOrderDTO deleteOrderDTO) {
        orderService.deleteOrder(deleteOrderDTO);
    }
}


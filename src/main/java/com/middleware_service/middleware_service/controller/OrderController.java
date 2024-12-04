package com.middleware_service.middleware_service.controller;

import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.exception.OrderException;
import com.middleware_service.middleware_service.service.order.BarOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final BarOrderService barOrderService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderTxDTO>> retrieveOrders(
            @RequestParam(value = "orderId", required = false) UUID orderId,
            @RequestParam(value = "userId", required = false) UUID userId
        ) throws OrderException {
        try {
            barOrderService.handleRetrieveOrders(orderId, userId);
        } catch (Exception e) {
            throw new OrderException("Error retrieving orders", e);
        }
    }

    @PostMapping(produces = "application/json")
    private ResponseEntity<String> createOrder(@RequestBody @Valid OrderRxDTO orderRxDTO) throws OrderException {
        try {
            barOrderService.handleCreateOrder(orderRxDTO);
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            throw new OrderException("Error creating order", e.getCause());
        }
    }

    @DeleteMapping
    private ResponseEntity<String> cancelOrder(@RequestParam(value = "orderId") UUID orderId) throws OrderException {
        try {
            barOrderService.handleCancelOrder(orderId);
        } catch (Exception e) {
            throw new OrderException("Error deleting order", e.getCause());
        }
    }

}

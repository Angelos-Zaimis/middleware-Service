package com.middleware_service.middleware_service.controller;

import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.service.order.BarOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final BarOrderService barOrderService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderTxDTO>>retrieveOrders(
            @RequestParam(value = "orderId", required = false) UUID orderId,
            @RequestParam(value = "userId") UUID userId ) {

        return ResponseEntity.ok(barOrderService.handleRetrieveOrders(orderId, userId));
    }

    @PostMapping(produces = "application/json")
    private ResponseEntity<String> createOrder(@RequestBody @Valid OrderRxDTO orderRxDTO) {
        barOrderService.handleCreateOrder(orderRxDTO);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping
    private ResponseEntity<String> cancelOrder(@RequestParam(value = "orderId") UUID orderId) {
        barOrderService.handleCancelOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

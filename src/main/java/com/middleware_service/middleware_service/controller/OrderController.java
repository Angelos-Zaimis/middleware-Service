package com.middleware_service.middleware_service.controller;

import com.middleware_service.middleware_service.configuration.springdoc.SpringDocTags;
import com.middleware_service.middleware_service.dto.order.CancelOrderDTO;
import com.middleware_service.middleware_service.dto.order.DeleteOrderDTO;
import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.service.order.BarOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = SpringDocTags.ORDERS)
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final BarOrderService barOrderService;

    @Operation(
            summary = "Returns all users orders or a specific order",
            description = "User with provided id can retrieve all his orders or a specific order"
    )
    @GetMapping(produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Retrieval successful")
    public ResponseEntity<List<OrderTxDTO>>retrieveOrders(
            @RequestParam(value = "orderId", required = false) UUID orderId,
            @RequestParam(value = "userId") UUID userId ) {

        return ResponseEntity.ok(barOrderService.handleRetrieveOrders(orderId, userId));
    }

    @Operation(
            summary = "Creates a new order",
            description = "This endpoint creates a new order with the provided details."
    )
    @PostMapping(produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Order created successfully")
    private ResponseEntity<String> createOrder(@RequestBody @Valid OrderRxDTO orderRxDTO) {
        barOrderService.handleCreateOrder(orderRxDTO);
        return ResponseEntity.ok("Order created successfully");
    }

    @Operation(
            summary = "Cancels an existing order",
            description = "This endpoint cancels an order based on the provided orderId and userId."
    )
    @PostMapping(path = "/cancel")
    @ApiResponse(responseCode = "200", description = "Order deleted successfully")
    private ResponseEntity<String> cancelOrder(@RequestBody @Valid CancelOrderDTO cancelOrderDTO) {
        barOrderService.handleCancelOrder(cancelOrderDTO);
        return ResponseEntity.ok("Order canceled successfully");
    }

    @Operation(
            summary = "Deletes an existing order",
            description = "This endpoint deletes an order based on the provided orderId and userId."
    )
    @DeleteMapping
    @ApiResponse(responseCode = "200", description = "Order deleted successfully")
    private ResponseEntity<String> deleteOrder(@RequestBody @Valid DeleteOrderDTO deleteOrderDTO) {
        barOrderService.handleDeleteOrder(deleteOrderDTO);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

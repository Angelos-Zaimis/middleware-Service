package com.middleware_service.middleware_service.controller;

import com.middleware_service.middleware_service.dto.orders.OrderRxDTO;
import com.middleware_service.middleware_service.exception.OrderException;
import com.middleware_service.middleware_service.service.order.BarOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final BarOrderService barOrderService;

    @PostMapping(produces = "application/json")
    private ResponseEntity<Void> createOrder(@RequestBody @Valid OrderRxDTO orderRxDTO) throws OrderException {
        try {
            barOrderService.handleCreateOrder(orderRxDTO);
        } catch (Exception e) {
            throw new OrderException("Error creating order", e.getCause());
        }
        return ResponseEntity.ok().build();
    }
}

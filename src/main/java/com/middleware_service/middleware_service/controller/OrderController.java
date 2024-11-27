package com.middleware_service.middleware_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/orders/")
public class OrderController {



    private ResponseEntity<Void> createOrder(Order order) {}
}

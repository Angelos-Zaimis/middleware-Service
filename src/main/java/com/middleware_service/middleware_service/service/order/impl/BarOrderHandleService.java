package com.middleware_service.middleware_service.service.order.impl;

import com.middleware_service.middleware_service.dto.orders.OrderRxDTO;
import com.middleware_service.middleware_service.exception.OrderDataAccessException;
import com.middleware_service.middleware_service.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarOrderHandleService implements OrderService {

    @Override
    @Transactional
    public void createOrder(OrderRxDTO orderRxDTO) {
        try {

        } catch (DataAccessException e) {
            throw new OrderDataAccessException("Error creating order", e);
        }
    }
}

package com.middleware_service.middleware_service.repository.order;

import com.middleware_service.middleware_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserId(UUID userId);

    Order findByIdAndUserId(UUID orderId, UUID userId);
}

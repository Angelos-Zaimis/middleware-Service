package com.middleware_service.middleware_service.repository.product;

import com.middleware_service.middleware_service.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<OrderProduct, UUID> {
}

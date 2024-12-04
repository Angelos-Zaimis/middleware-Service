package com.middleware_service.middleware_service.service.order.impl;

import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
import com.middleware_service.middleware_service.dto.product.ProductTxDTO;
import com.middleware_service.middleware_service.entity.Order;
import com.middleware_service.middleware_service.entity.OrderProduct;
import com.middleware_service.middleware_service.enums.Order_status;
import com.middleware_service.middleware_service.exception.OrderDataAccessException;
import com.middleware_service.middleware_service.mapper.OrderMapper;
import com.middleware_service.middleware_service.mapper.ProductMapper;
import com.middleware_service.middleware_service.repository.order.OrderRepository;
import com.middleware_service.middleware_service.service.kafka.KafkaService;
import com.middleware_service.middleware_service.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarOrderHandleService implements OrderService {

    private final OrderMapper orderMapper;
    private final KafkaService kafkaService;
    private final ProductMapper productMapper;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public List<OrderTxDTO> retrieveOrders(UUID orderId, UUID userId) {
        if (orderId == null) {
            return retrieveAllUserOrders(userId);
        }

        return retrieveOrders(orderId, userId);
    }

    private List<OrderTxDTO> retrieveAllUserOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId).stream().map(orderMapper::mapToTxDTO).toList();
    }

    private List<OrderTxDTO> retrieveOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId);
        OrderTxDTO orderTxDTO = orderMapper.mapToTxDTO(order);
        List<OrderTxDTO> orderTxDTOS = new ArrayList<>();
        orderTxDTOS.add(orderTxDTO);

        return orderTxDTOS;
    }

    @Override
    @Transactional
    public void createOrder(OrderRxDTO orderRxDTO) {
        try {
            Order newOrder = createNewOrder(orderRxDTO);
            orderRepository.save(newOrder);
        } catch (DataAccessException e) {
            throw new OrderDataAccessException("Error creating order", e);
        }
    }

    private Order createNewOrder(OrderRxDTO orderRxDTO) {
       Order newOrder = orderMapper.map(orderRxDTO);
       newOrder.setStatus(Order_status.PENDING);

       assignProductsToOrder(newOrder, orderRxDTO);

       return newOrder;
    }

    private void assignProductsToOrder(Order newOrder, OrderRxDTO orderRxDTO) {
        List<ProductTxDTO> productList = retrieveProducts(orderRxDTO.getProductIds());

        newOrder.setProducts(mapProductToOrderProduct(productList));
    }

    private List<ProductTxDTO> retrieveProducts(List<UUID> productIds) {
        return kafkaService.retrieveProducts(productIds);
    }

    private Set<OrderProduct> mapProductToOrderProduct(List<ProductTxDTO> productTxDTOS) {
        return productTxDTOS.stream().map(productMapper::map).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void cancelOrder(UUID orderId) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);

            if (order.isPresent()) {
                order.get().setStatus(Order_status.CANCELLED);
                orderRepository.save(order.get());
            }
        } catch (DataAccessException e) {
            throw new OrderDataAccessException("Error canceling order", e);
        }
    }
}

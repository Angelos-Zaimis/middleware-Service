package com.middleware_service.middleware_service.service.order.impl;

import com.middleware_service.middleware_service.dto.order.*;
import com.middleware_service.middleware_service.entity.Order;
import com.middleware_service.middleware_service.enums.Order_status;
import com.middleware_service.middleware_service.exceptions.ResourceNotFoundException;
import com.middleware_service.middleware_service.mapper.OrderMapper;
import com.middleware_service.middleware_service.repository.order.OrderRepository;
import com.middleware_service.middleware_service.service.kafka.KafkaService;
import com.middleware_service.middleware_service.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarOrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final KafkaService kafkaService;

    @Override
    @Transactional
    public List<OrderTxDTO> retrieveOrders(UUID orderId, UUID userId) {
        if (orderId == null) {
            return retrieveAllUserOrders(userId);
        }

        return retrieveOrder(orderId, userId);
    }

    private List<OrderTxDTO> retrieveAllUserOrders(UUID userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Orders not found for user: " + userId);
        }

        return orders.stream().map(orderMapper::mapToTxDTO).toList();
    }

    private List<OrderTxDTO> retrieveOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found for user: " + userId);
        }

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        return orders.stream().map(orderMapper::mapToTxDTO).toList();
    }

    @Override
    @Transactional
    public OrderTxDTO createOrder(OrderRxDTO orderRxDTO) {
        Order newOrder = createNewOrder(orderRxDTO);

        updateInventory(orderRxDTO);

        return orderMapper.mapToTxDTO(newOrder);
    }

    private Order createNewOrder(OrderRxDTO orderRxDTO) {
       Order newOrder = orderMapper.map(orderRxDTO);
       newOrder.setStatus(Order_status.PENDING);

       addProductsToOrders(orderRxDTO, newOrder);

        return orderRepository.save(newOrder);
    }

    private void addProductsToOrders(OrderRxDTO orderRxDTO, Order newOrder) {
        List<UUID> productIds = new ArrayList<>();

        for (ProductDTO pr : orderRxDTO.getProducts()) {
            productIds.add(pr.getProductId());
        }

        newOrder.setProducts(productIds);
    }

    private void updateInventory(OrderRxDTO orderRxDTO) {
        UpdateInventoryDTO updateInventoryDTO = createUpdateInventoryDTO(orderRxDTO);

        kafkaService.sendKafkaUpdateInventoryMessage(updateInventoryDTO);
    }

    private UpdateInventoryDTO createUpdateInventoryDTO(OrderRxDTO orderRxDTO) {
        UpdateInventoryDTO updateInventoryDTO = new UpdateInventoryDTO();
        updateInventoryDTO.setUserId(orderRxDTO.getUserId());

        for (ProductDTO productDTO : orderRxDTO.getProducts()) {
            updateInventoryDTO.getProducts().add(productDTO);
        }

        return updateInventoryDTO;
    }

    @Override
    @Transactional
    public void cancelOrder(CancelOrderDTO cancelOrderDTO) {
        Order order = orderRepository.findByIdAndUserId(cancelOrderDTO.getOrderId(), cancelOrderDTO.getUserId());

        if (order == null) {
            throw new ResourceNotFoundException("Order not found for user: " + cancelOrderDTO.getUserId());
        }

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(DeleteOrderDTO deleteOrderDTO) {
        Order order = orderRepository.findByIdAndUserId(deleteOrderDTO.getOrderId(), deleteOrderDTO.getUserId());

        if (order == null) {
            throw new ResourceNotFoundException("Order not found for user: " + deleteOrderDTO.getUserId());
        }

        orderRepository.delete(order);
    }
}

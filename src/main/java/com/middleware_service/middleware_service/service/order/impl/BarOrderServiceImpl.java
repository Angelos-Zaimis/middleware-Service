package com.middleware_service.middleware_service.service.order.impl;

import com.middleware_service.middleware_service.configuration.kafka.KafkaTopics;
import com.middleware_service.middleware_service.dto.order.CancelOrderDTO;
import com.middleware_service.middleware_service.dto.order.DeleteOrderDTO;
import com.middleware_service.middleware_service.dto.order.OrderRxDTO;
import com.middleware_service.middleware_service.dto.order.OrderTxDTO;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarOrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;

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
       orderRepository.save(newOrder);

        return newOrder;
    }

    private void updateInventory(OrderRxDTO orderRxDTO) {

        sendKafkaMessage(orderRxDTO);
    }

    private void sendKafkaMessage(OrderRxDTO orderRxDTO) {
        var result = kafkaTemplate.send(KafkaTopics.UPDATE_INVENTORY, orderRxDTO);
        result.whenComplete((msg, ex) -> {
            if (Objects.nonNull(ex)) {
                log.warn("Producer send message unsuccessfully for event update inventory", ex);
            } else {
                log.debug("Producer send message successfully for event update inventory");
            }
        });
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

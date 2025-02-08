package com.middleware_service.middleware_service.service.kafka;

import com.middleware_service.middleware_service.configuration.kafka.KafkaTopics;
import com.middleware_service.middleware_service.dto.order.UpdateInventoryDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class KafkaService {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendKafkaUpdateInventoryMessage(UpdateInventoryDTO updateInventoryDTO) {
        var result = kafkaTemplate.send(KafkaTopics.UPDATE_INVENTORY, updateInventoryDTO);
        result.whenComplete((msg, ex) -> {
            if (Objects.nonNull(ex)) {
                log.warn("Producer send message unsuccessfully for event update inventory", ex);
            } else {
                log.debug("Producer send message successfully for event update inventory");
            }
        });
    }
}


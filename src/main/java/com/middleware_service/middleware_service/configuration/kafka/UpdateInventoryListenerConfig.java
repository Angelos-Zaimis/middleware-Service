package com.middleware_service.middleware_service.configuration.kafka;

import com.middleware_service.middleware_service.dto.order.UpdateInventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class UpdateInventoryListenerConfig extends AbstractKafkaConsumerConfig<UpdateInventoryDTO> {

    @Autowired
    public UpdateInventoryListenerConfig(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        super(bootstrapServers);
    }

    @Override
    public String getKafkaGroup() {
        return KafkaGroup.UPDATE_INVENTORY_GROUP;
    }

    @Override
    public Class<UpdateInventoryDTO> getValueDeserializerClassConfig() {
        return UpdateInventoryDTO.class;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UpdateInventoryDTO> updateInventoryListenerContainerFactory() {
        return kafkaListenerContainerFactory();
    }
}

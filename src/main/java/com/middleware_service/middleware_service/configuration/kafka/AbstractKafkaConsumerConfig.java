package com.middleware_service.middleware_service.configuration.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.jsoup.internal.StringUtil;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKafkaConsumerConfig<T> {
    private final String bootstrapServers;
    
    protected AbstractKafkaConsumerConfig(String bootstrapServers) {this.bootstrapServers = bootstrapServers;}
    
    public abstract String getKafkaGroup();
    
    public abstract Class<T> getValueDeserializerClassConfig();
    
    protected ConsumerFactory<String, T> consumerFactory() {
        if (StringUtil.isBlank(bootstrapServers)) {
            throw new RuntimeException("bootstrapServers is empty");
        } else {
            JsonDeserializer jsonDeserializer = new JsonDeserializer(this.getValueDeserializerClassConfig());
            jsonDeserializer.addTrustedPackages(new String[]{"com.*"});
            jsonDeserializer.setUseTypeHeaders(false);
            Map<String, Object> props = new HashMap<>();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("group.id", getKafkaGroup());
            return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
        }
    }

    protected ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory());
        return factory;
    }
}

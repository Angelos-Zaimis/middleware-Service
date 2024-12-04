package com.middleware_service.middleware_service.service.kafka;

import com.middleware_service.middleware_service.dto.product.ProductTxDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaService {

    public List<ProductTxDTO> retrieveProducts(List<UUID> productIds) {
        return new ArrayList<>();
    }
}


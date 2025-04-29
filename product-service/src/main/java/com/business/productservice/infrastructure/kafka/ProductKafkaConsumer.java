package com.business.productservice.infrastructure.kafka;

import com.business.productservice.application.service.v3.ProductServiceApiV3;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductKafkaConsumer {

    private final ProductServiceApiV3 productService;

    @KafkaListener(topics = "stock-decrease-topic", groupId = "product-service-group")
    public void consumeStockDecrease(String productId){
        UUID id = UUID.fromString(productId);
        productService.postDecreaseById(id);
    }

    @KafkaListener(topics = "stock-restore-topic", groupId = "product-service-group")
    public void consumeStockRestore(String productId){
        UUID id = UUID.fromString(productId);
        productService.postRestoreById(id);
    }
}

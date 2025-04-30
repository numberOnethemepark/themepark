package com.business.productservice.infrastructure.kafka;

import com.business.productservice.application.service.v3.ProductServiceApiV3;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductKafkaConsumer {

    private final ProductServiceApiV3 productService;

    @KafkaListener(topics = "stock-decrease-topic", groupId = "product-service-group")
    public void consumeStockDecrease(String productId, Acknowledgment ack){
        try {
            UUID id = UUID.fromString(productId);
            // 의도적으로 에러 발생
//            if (true) {
//                throw new RuntimeException("강제로 에러 발생 (DLQ 테스트)");
//            }
            productService.postDecreaseById(id);

            ack.acknowledge(); //성공 > ack 호출

        } catch (Exception e) {
            // 실패하면 ack 호출 안 함 > Spring Kafka가 재시도, DLQ 이동
            throw e;
        }
    }

    @KafkaListener(topics = "stock-restore-topic", groupId = "product-service-group")
    public void consumeStockRestore(String productId, Acknowledgment ack){
        try {
            UUID id = UUID.fromString(productId);
            productService.postRestoreById(id);

            ack.acknowledge();

        } catch (Exception e) {
            throw e;
        }
    }
}

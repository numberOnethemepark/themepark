package com.business.productservice.infrastructure.kafka;

import com.business.productservice.application.service.v3.ProductServiceApiV3;
import com.business.productservice.infrastructure.kafka.dto.ReqStockDecreaseDTOApiV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductKafkaConsumer {

    private final ProductServiceApiV3 productService;

    @KafkaListener(topics = "stock-decrease-topic", groupId = "product-service-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeStockDecrease(ReqStockDecreaseDTOApiV3 dto, Acknowledgment ack){
        try {
            // 의도적으로 에러 발생
//            if (true) {
//                throw new RuntimeException("강제로 에러 발생 (DLQ 테스트)");
//            }
            productService.postDecreaseById(dto.getProductId(), dto.getOrderId());
            ack.acknowledge(); //성공 > ack 호출

        } catch (Exception e) {
            // 실패하면 ack 호출 안 함 > Spring Kafka가 재시도, DLQ 이동
            throw e;
        }
    }

    @KafkaListener(topics = "stock-restore-topic", groupId = "product-service-group",
            containerFactory = "stringKafkaListenerContainerFactory")
    public void consumeStockRestore(String productId, Acknowledgment ack){
        try {
            UUID id = UUID.fromString(productId.replace("\"", ""));
            productService.postRestoreById(id);

            ack.acknowledge();

        } catch (Exception e) {
            throw e;
        }
    }
}

package com.business.productservice.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
* 테스트 용 프로듀서 파일
* */
@RequiredArgsConstructor
@Service
public class TestKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendStockDecreaseTest(UUID productId) {
        kafkaTemplate.send("stock-decrease-topic", productId.toString());
    }
}

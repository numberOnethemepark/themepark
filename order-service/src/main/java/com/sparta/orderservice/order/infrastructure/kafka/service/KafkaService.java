package com.sparta.orderservice.order.infrastructure.kafka.service;

import com.sparta.orderservice.order.infrastructure.kafka.dto.req.ReqProductKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderAsync(String topic, ReqProductKafkaDto dto) {
        kafkaTemplate.send(topic, dto)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka 전송 실패: {}", dto, ex);
                    } else {
                        log.info("Kafka 전송 성공: {}", dto);
                    }
                });
    }

    public void sendRestoreAsync(String topic, String productId){
        kafkaTemplate.send(topic, productId)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("kafka 전송 실패: {}", productId, ex);
                    }else{
                        log.info("kafka 전송 성공: {}", productId);
                    }
                });
    }
}

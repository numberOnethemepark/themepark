package com.sparta.orderservice.order.infrastructure.kafka.consumer;

import com.sparta.orderservice.order.infrastructure.kafka.dto.res.ResProductKafkaDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    @KafkaListener(topics = "stock-decrease-success-topic", groupId = "order-service")
    public void stockDecreaseSuccess(ResProductKafkaDto resProductKafkaDto) {
        System.out.println(resProductKafkaDto.getReason());
    }

    @KafkaListener(topics = "stock-decrease-fail-topic", groupId = "order-service")
    public void stockDecreaseFail(ResProductKafkaDto resProductKafkaDto) {
        System.out.println(resProductKafkaDto.getReason());
    }
}

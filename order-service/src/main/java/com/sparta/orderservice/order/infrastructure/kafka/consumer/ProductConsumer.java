package com.sparta.orderservice.order.infrastructure.kafka.consumer;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.kafka.dto.res.ResProductKafkaDto;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {

    private final OrderRepository orderRepository;

    @Transactional
    @KafkaListener(topics = "stock-decrease-success-topic", groupId = "order-service")
    public void stockDecreaseSuccess(ResProductKafkaDto resProductKafkaDto) {
        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(resProductKafkaDto.getOrderId()));
        OrderEntity.updateOrder(orderEntity, null, PaymentStatus.NOT_PAID);
        log.info("재고 차감을 완료하였습니다. 결제를 진행해주세요.");
    }

    @KafkaListener(topics = "stock-decrease-fail-topic", groupId = "order-service")
    public void stockDecreaseFail(ResProductKafkaDto resProductKafkaDto) {
        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(resProductKafkaDto.getOrderId()));
        orderRepository.delete(orderEntity);
        log.info("재고 차감에 실패하였습니다. 다시 주문해주세요.");
    }
}

package com.sparta.orderservice.order.infrastructure.repository;

import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.order.application.exception.OrderExceptionCode;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataJpaOrderRepository jpaOrderRepository;

    public JpaOrderRepository(SpringDataJpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public void save(OrderEntity orderEntity){
        jpaOrderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findById(UUID orderId) { return jpaOrderRepository.findById(orderId).orElseThrow(()->new CustomException(OrderExceptionCode.ORDER_NOT_FOUND)); }

    @Override
    public Page<OrderEntity> findByUserId(Long userId, Pageable pageable){return jpaOrderRepository.findByUserId(userId, pageable);}

    @Override
    public void delete(OrderEntity orderEntity) {
        jpaOrderRepository.delete(orderEntity);
    }
}

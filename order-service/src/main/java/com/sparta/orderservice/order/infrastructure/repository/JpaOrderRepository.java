package com.sparta.orderservice.order.infrastructure.repository;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

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
}

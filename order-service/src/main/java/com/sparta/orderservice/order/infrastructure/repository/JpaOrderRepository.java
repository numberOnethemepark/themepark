package com.sparta.orderservice.order.infrastructure.repository;

import com.sparta.orderservice.order.domain.entity.Order;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataJpaOrderRepository jpaRepository;

    public JpaOrderRepository(SpringDataJpaOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Order order){
        jpaRepository.save(order);
    }
}

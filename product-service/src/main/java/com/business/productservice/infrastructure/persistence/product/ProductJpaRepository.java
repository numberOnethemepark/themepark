package com.business.productservice.infrastructure.persistence.product;

import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.domain.product.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID>, ProductRepository, QuerydslPredicateExecutor<ProductEntity> {
}

package com.business.productservice.domain.product.repository;

import com.business.productservice.domain.product.entity.ProductEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Page<ProductEntity> findAll(Predicate predicate, Pageable pageable);
}

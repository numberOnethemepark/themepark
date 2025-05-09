package com.business.productservice.domain.product.repository;

import com.business.productservice.domain.product.entity.StockEntity;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository {
    Optional<StockEntity> findByIdWithPessimisticLock(UUID id);
}

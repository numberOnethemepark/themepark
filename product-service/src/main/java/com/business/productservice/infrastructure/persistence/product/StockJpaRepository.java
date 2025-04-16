package com.business.productservice.infrastructure.persistence.product;

import com.business.productservice.domain.product.entity.StockEntity;
import com.business.productservice.domain.product.repository.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID>, StockRepository {
}

package com.business.productservice.infrastructure.persistence.product;

import com.business.productservice.domain.product.entity.StockEntity;
import com.business.productservice.domain.product.repository.StockRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID>, StockRepository{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM StockEntity s WHERE s.id = :id")
    Optional<StockEntity> findByIdWithPessimisticLock(@Param("id") UUID id);
}

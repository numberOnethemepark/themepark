package com.business.productservice.domain.product.repository;

import com.business.productservice.domain.product.entity.StockEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class StockRepositoryImpl implements StockRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<StockEntity> findByIdWithPessimisticLock(UUID id) {
        StockEntity stock = em.createQuery(
                        "SELECT s FROM StockEntity s WHERE s.id = :id", StockEntity.class)
                .setParameter("id", id)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();

        return Optional.ofNullable(stock);
    }
}

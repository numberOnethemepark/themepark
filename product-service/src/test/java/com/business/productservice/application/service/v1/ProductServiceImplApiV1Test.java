package com.business.productservice.application.service.v1;

import com.business.productservice.domain.product.entity.StockEntity;
import com.business.productservice.infrastructure.persistence.product.StockJpaRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class ProductServiceImplApiV1Test {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplApiV1.class);

    @Autowired
    private ProductServiceImplApiV1 productService;

    @Autowired
    private StockJpaRepository stockRepository;

    @Test
    public void testPessimisticLocking() throws InterruptedException {
        UUID stockId = UUID.fromString("330592a8-f6ab-465c-833e-c108e4b56e10");

        Thread thread1 = new Thread(() -> {
            try {
                logger.info("Thread 1: 재고 차감 시도");
                productService.postDecreaseById(stockId);
                logger.info("Thread 1: 성공");
            } catch (Exception e) {
                logger.warn("Thread 1: 실패 - {}", e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                logger.info("Thread 2: 재고 차감 시도");
                productService.postDecreaseById(stockId);
                logger.info("Thread 2: 성공");
            } catch (Exception e) {
                logger.warn("Thread 2: 실패 - {}", e.getMessage());
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // then - 최종 재고 확인
        StockEntity result = stockRepository.findById(stockId).orElseThrow();
        logger.info("최종 재고 수량: {}", result.getStock());
    }



}

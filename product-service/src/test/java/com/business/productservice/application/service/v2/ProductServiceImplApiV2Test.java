//package com.business.productservice.application.service.v2;
//
//import com.business.productservice.application.exception.ProductExceptionCode;
//import com.business.productservice.domain.product.entity.StockEntity;
//import com.business.productservice.infrastructure.persistence.product.StockJpaRepository;
//import com.github.themepark.common.application.exception.CustomException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@SpringBootTest
//public class ProductServiceImplApiV2Test {
//    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplApiV2.class);
//
//    @Autowired
//    private ProductServiceImplApiV2 productService;
//
//    @Autowired
//    private StockJpaRepository stockRepository;
//
//    @Test
//    void 재고_50개만_차감되는지_테스트() throws InterruptedException {
//        UUID stockId = UUID.fromString("330592a8-f6ab-465c-833e-c108e4b56e10");
//        int threadCount = 50;
//
//        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        AtomicInteger success = new AtomicInteger(0);
//        AtomicInteger fail = new AtomicInteger(0);
//
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    productService.postDecreaseById(stockId);
//                    success.incrementAndGet(); // 여기가 성공 카운트
//                } catch (CustomException e) {
//                    if (e.getExceptionCode() == ProductExceptionCode.PRODUCT_STOCK_SOLDOUT) {
//                        fail.incrementAndGet();
//                    } else {
//                        System.out.println("기타 예외: " + e.getMessage());
//                    }
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await();
//        executorService.shutdown();
//
//        StockEntity result = stockRepository.findById(stockId).orElseThrow();
//        System.out.println("성공 수: " + success.get());
//        System.out.println("실패 수: " + fail.get());
//        System.out.println("최종 재고 수량: " + result.getStock());
//        Assertions.assertThat(result.getStock()).isEqualTo(50);
//    }
//}

package com.business.productservice.application.service.v2;

import com.business.productservice.application.dto.v2.request.ReqProductPostDTOApiV2;
import com.business.productservice.application.dto.v2.request.ReqProductPutDTOApiV2;
import com.business.productservice.application.dto.v2.response.*;
import com.business.productservice.application.exception.ProductExceptionCode;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.domain.product.entity.StockEntity;
import com.business.productservice.infrastructure.dto.ReqToSlackPostDTOApiV1;
import com.business.productservice.infrastructure.feign.SlackFeignClientApiV1;
import com.business.productservice.infrastructure.persistence.product.ProductJpaRepository;
import com.business.productservice.infrastructure.persistence.product.StockJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImplApiV2 implements ProductServiceApiV2 {

    private final ProductJpaRepository productRepository;
    private final StockJpaRepository stockRepository;
    private final SlackFeignClientApiV1 slackFeignClientApiV1;
    private final RedissonClient redissonClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResProductPostDTOApiV2 postBy(ReqProductPostDTOApiV2 reqDto) {
        ProductEntity product = reqDto.toEntityWithStock();

        ProductEntity savedProduct = productRepository.save(product);

        return ResProductPostDTOApiV2.of(savedProduct);
    }

    @Override
    public ResProductGetByIdDTOApiV2 getBy(UUID id){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));
        return ResProductGetByIdDTOApiV2.of(productEntity);
    }


    @Override
    public ResProductGetDTOApiV2 getBy(Predicate predicate, Pageable pageable){

        Page<ProductEntity> productEntityPage = productRepository.findAll(predicate, pageable);
        return ResProductGetDTOApiV2.of(productEntityPage);
    }

    @Override
    public ResProductPutDTOApiV2 putBy(UUID id, ReqProductPutDTOApiV2 dto) {

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));

        productEntity.update(
                dto.getProduct().getName(),
                dto.getProduct().getDescription(),
                dto.getProduct().getProductType(),
                dto.getProduct().getPrice(),
                dto.getProduct().getEventStartAt(),
                dto.getProduct().getEventEndAt(),
                dto.getProduct().getLimitQuantity(),
                dto.getProduct().getProductStatus()
        );
        return ResProductPutDTOApiV2.of(productEntity);
    }

    @Override
    public void deleteBy(UUID id) {

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));
        StockEntity stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));

        productEntity.deletedBy(1L);
        stockEntity.deletedBy(1L);
    }

    @Override
    @Transactional
    public void postDecreaseById(UUID id) {
        String lockKey = "lock:stock:"+id;
        RLock lock = redissonClient.getLock(lockKey);

        boolean isLocked = false;
        try{
            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS); // waitTime: 5초, leaseTime: 3초

            System.out.println("현재 락 키 존재 여부: " + redissonClient.getKeys().getKeysStream()
                    .filter(key -> key.startsWith("lock:"))
                    .toList());

            if (!isLocked) {
                throw new CustomException(ProductExceptionCode.LOCK_FAILED);
            }

            StockEntity stockEntity = stockRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));

            if (stockEntity.getStock() <= 0) {
                throw new CustomException(ProductExceptionCode.PRODUCT_STOCK_SOLDOUT);
            }

            stockEntity.decrease();

            String cacheKey = "stock:" + id;
            redisTemplate.opsForValue().set(cacheKey, stockEntity.getStock());

            if (stockEntity.getStock() == 0) {
                sendStockSoldOutSlack(stockEntity);
            }
        } catch(InterruptedException e){
            throw new CustomException(ProductExceptionCode.LOCK_INTERRUPTED);
        } finally{
            if (isLocked) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        lock.unlock();
                    }
                });
            }
        }

    }

    @Override
    public void postRestoreById(UUID id) {
        StockEntity stockEntity = stockRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));

        stockEntity.restore();
    }

    @Override
    public ResStockGetByIdDTOApiV2 getStockById(UUID id){
        StockEntity stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));
        return ResStockGetByIdDTOApiV2.of(stockEntity);
    }

    private void sendStockSoldOutSlack(StockEntity stockEntity) {
        String productName = stockEntity.getProduct().getName();

        ReqToSlackPostDTOApiV1 request = ReqToSlackPostDTOApiV1.createStockSoldOutMessage(productName);

        try {
            slackFeignClientApiV1.postBy(request);
        } catch (Exception e) {
            log.warn("슬랙 전송 실패 - 상품명: {}", productName, e);
        }
    }

}

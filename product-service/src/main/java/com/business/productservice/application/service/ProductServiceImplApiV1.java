package com.business.productservice.application.service;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.response.ResProductGetByIdDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPostDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPutDTOApiV1;
import com.business.productservice.application.exception.ProductExceptionCode;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.infrastructure.persistence.product.ProductJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImplApiV1 implements ProductServiceApiV1{

    private final ProductJpaRepository productRepository;

    @Override
    public ResProductPostDTOApiV1 postBy(ReqProductPostDTOApiV1 reqDto) {
        ProductEntity product = reqDto.toEntityWithStock();

        ProductEntity savedProduct = productRepository.save(product);

        return ResProductPostDTOApiV1.of(savedProduct);
    }

    @Override
    public ResProductGetByIdDTOApiV1 getBy(UUID id){
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));
        return ResProductGetByIdDTOApiV1.of(productEntity);
    }

    @Override
    public ResProductPutDTOApiV1 putBy(UUID id, ReqProductPutDTOApiV1 dto) {

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
        return ResProductPutDTOApiV1.of(productEntity);
    }

    @Override
    public void deleteBy(UUID id) {

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ProductExceptionCode.PRODUCT_NOT_FOUND));

        productEntity.deletedBy(1L);
    }

}

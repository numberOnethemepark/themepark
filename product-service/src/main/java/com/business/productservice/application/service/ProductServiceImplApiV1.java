package com.business.productservice.application.service;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPostDTOApiV1;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.infrastructure.persistence.product.ProductJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}

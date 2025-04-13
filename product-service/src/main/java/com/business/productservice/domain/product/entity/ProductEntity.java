package com.business.productservice.domain.product.entity;

import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(name = "product_status", nullable = false)
    private ProductStatus status;

    @Column(name = "limit_quantity", nullable = false)
    private Integer limitQuantity;

    @Column(name = "event_start_at", nullable = false)
    private LocalDateTime eventStartAt;

    @Column(name = "event_end_at", nullable = false)
    private LocalDateTime eventEndTAt;
}

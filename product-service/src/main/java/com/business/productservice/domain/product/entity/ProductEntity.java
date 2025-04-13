package com.business.productservice.domain.product.entity;

import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_products")
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name ="product_price", nullable = false)
    private Integer price;

    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(name = "product_status", nullable = false)
    private ProductStatus productStatus = ProductStatus.DRAFT;

    @Column(name = "limit_quantity", nullable = false)
    private Integer limitQuantity;

    @Column(name = "event_start_at", nullable = false)
    private LocalDateTime eventStartAt;

    @Column(name = "event_end_at", nullable = false)
    private LocalDateTime eventEndAt;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private StockEntity stock = new StockEntity();

    @Builder
    public ProductEntity(String name, String description, ProductType productType,
                         Integer price, LocalDateTime eventStartAt, LocalDateTime eventEndAt,
                         Integer limitQuantity, ProductStatus productStatus) {
        this.name = name;
        this.description = description;
        this.productType = productType;
        this.price = price;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
        this.limitQuantity = limitQuantity;
        this.productStatus = (productStatus != null) ? productStatus : ProductStatus.DRAFT;
    }

    public static ProductEntity createWithStock(String name, String description, ProductType productType,
                                                Integer price, LocalDateTime eventStartAt, LocalDateTime eventEndAt,
                                                Integer limitQuantity, ProductStatus productStatus) {
        ProductEntity product = ProductEntity.builder()
                .name(name)
                .description(description)
                .productType(productType)
                .price(price)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .limitQuantity(limitQuantity)
                .productStatus(productStatus != null ? productStatus : ProductStatus.DRAFT)
                .build();

        StockEntity stock = new StockEntity(product, limitQuantity); // 생성자에서 연관관계 연결
        product.addStock(stock); // 양방향 설정

        return product;
    }

    public void addStock(StockEntity stock) {
        this.stock=stock;
    }

}

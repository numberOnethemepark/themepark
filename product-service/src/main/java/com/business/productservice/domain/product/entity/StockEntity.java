package com.business.productservice.domain.product.entity;

import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_product_stocks")
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity extends BaseEntity {

    @Id
    @Column(name = "product_stock_id", nullable = false)  // 새로운 ID 필드로 설정
    private UUID productStockId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_stock_id", referencedColumnName = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    public StockEntity(ProductEntity product, Integer stock) {
        this.product = product;
        this.productStockId = product.getId(); // MapsId 동기화
        this.stock = stock;

        // 양방향 연관관계 연결
        if (product.getStock() == null) {
            product.addStock(this);
        }
    }

    public void updateQuantity(int quantity) {
        this.stock = quantity;
    }



}

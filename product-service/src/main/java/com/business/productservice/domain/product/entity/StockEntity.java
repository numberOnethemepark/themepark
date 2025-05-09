package com.business.productservice.domain.product.entity;

import com.business.productservice.application.exception.ProductExceptionCode;
import com.business.productservice.domain.product.vo.ProductType;
import com.github.themepark.common.application.exception.CustomException;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_product_stocks")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
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

    public void decrease() {
        if(!product.getProductType().equals(ProductType.EVENT)){
            return;
        }
        this.stock--;
    }

    public void restore(){
        if(!product.getProductType().equals(ProductType.EVENT)){
            return;
        }
        if (this.stock >= product.getLimitQuantity()) {
            log.error("재고 복구 수량 초과 요청- productId: {}, 현재 재고: {}, 제한 수량: {}",
                    product.getId(), this.stock, product.getLimitQuantity());
            throw new CustomException(ProductExceptionCode.PRODUCT_STOCK_RESTORE_LIMIT_EXCEEDED);
        }
        this.stock++;
    }

}

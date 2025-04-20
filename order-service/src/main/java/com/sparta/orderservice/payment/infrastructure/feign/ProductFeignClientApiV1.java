package com.sparta.orderservice.payment.infrastructure.feign;

import com.github.themepark.common.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(
        name = "ProductOrderClient",
        url = "http://localhost:8004/v1/products",
        configuration = FeignConfig.class
)
public interface ProductFeignClientApiV1 {
    @GetMapping("/{id}/stock")
    void getStockById(@PathVariable UUID id);

    @PostMapping("/{id}/stocks-decrease")
    void postDecreaseById(@PathVariable UUID id);

    @PostMapping("/{id}/stocks-restore")
    void postRestoreById(@PathVariable UUID id);
}

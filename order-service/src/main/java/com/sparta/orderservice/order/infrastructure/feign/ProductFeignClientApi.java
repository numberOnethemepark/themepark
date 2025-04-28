package com.sparta.orderservice.order.infrastructure.feign;

import com.github.themepark.common.application.dto.ResDTO;
import com.github.themepark.common.infrastructure.config.FeignConfig;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(
        name = "product-service-client",
        url = "http://localhost:8004",
        configuration = FeignConfig.class
)
public interface ProductFeignClientApi {
    @GetMapping("/v1/products/{id}/stock")
    void getStockById(@PathVariable UUID id);

    @PostMapping("/v1/products/internal/{id}/stocks-decrease")
    void postDecreaseById(@PathVariable UUID id);

    @PostMapping("/v1/products/internal/{id}/stocks-restore")
    void postRestoreById(@PathVariable UUID id);

    @GetMapping("/v1/products/{id}")
    ResDTO<ResProductGetByIdDTOApi> getBy(@PathVariable UUID id);
}

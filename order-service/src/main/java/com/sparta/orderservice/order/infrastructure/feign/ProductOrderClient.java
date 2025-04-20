package com.sparta.orderservice.order.infrastructure.feign;

import com.github.themepark.common.application.dto.ResDTO;
import com.github.themepark.common.infrastructure.config.FeignConfig;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(
        name = "product-service-client",
        url = "http://localhost:8004/v1/products",
        configuration = FeignConfig.class
)
public interface ProductOrderClient {
    @GetMapping("/{id}/stock")
    void getStockById(@PathVariable UUID id);

    @PostMapping("/{id}/stocks-decrease")
    void postDecreaseById(@PathVariable UUID id);

    @PostMapping("/{id}/stocks-restore")
    void postRestoreById(@PathVariable UUID id);

    @GetMapping("{id}")
    ResDTO<ResProductGetByIdDTOApiV1> getBy(@PathVariable UUID id);
}

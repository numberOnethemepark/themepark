package com.business.productservice.infrastructure.feign;

import com.business.productservice.infrastructure.kafka.dto.ReqToSlackPostDTOApiV1;
import com.github.themepark.common.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slack-service",
        url = "http://localhost:8006",configuration = FeignConfig.class)
public interface SlackFeignClientApiV1 {

    @PostMapping("/v1/internal/slacks")
    ResponseEntity<Void> postBy(@RequestBody ReqToSlackPostDTOApiV1 dto);
}

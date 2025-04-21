package com.business.themeparkservice.waiting.infastructure.feign;

import com.business.themeparkservice.waiting.infastructure.dto.request.ReqSlackPostDTOApiV1;
import com.github.themepark.common.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slack-service",
        url = "http://localhost:8006/v1/slacks",configuration = FeignConfig.class)
public interface SlackFeignClientApiV1 {

    @PostMapping
    void postBy(@RequestBody ReqSlackPostDTOApiV1 reqDto);
}

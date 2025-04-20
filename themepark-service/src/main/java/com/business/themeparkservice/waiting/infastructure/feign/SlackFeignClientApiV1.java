package com.business.themeparkservice.waiting.infastructure.feign;

import com.github.themepark.common.application.dto.ResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "slack-service",url = "http://localhost:8080/v1/slacks")
public interface SlackFeignClientApiV1 {

    @PostMapping("/waiting/call")
    public ResDTO<?> waitingCall();
}

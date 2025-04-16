package com.sparta.orderservice.payment.infrastructure.client;

import com.sparta.orderservice.payment.application.dto.request.ReqPaymentTossDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import com.sparta.orderservice.payment.infrastructure.config.TossFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "tossPaymentsClient",
        url = "https://api.tosspayments.com/v1/payments",
        configuration = TossFeignConfig.class)
public interface TossPaymentsClient {
    @PostMapping("/confirm")
    ResponseEntity<ResPaymentTossDto> confirmPayment(@RequestBody ReqPaymentTossDto request);
}

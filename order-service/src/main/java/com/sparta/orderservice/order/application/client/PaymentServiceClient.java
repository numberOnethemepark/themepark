package com.sparta.orderservice.order.application.client;

import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.order.application.dto.response.ResPaymentPostDtoApiV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient
public interface PaymentServiceClient {

    @PostMapping("/v1/payments")
    ResponseEntity<ResDTO<ResPaymentPostDtoApiV1>> PostPayment(@RequestBody ReqPaymentPostDtoApiV1 reqPaymentPostDtOApiV1);

}

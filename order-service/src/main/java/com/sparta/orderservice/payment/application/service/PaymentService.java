package com.sparta.orderservice.payment.application.service;

import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.order.application.facade.v1.OrderFacadeV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.payment.application.dto.request.ReqPaymentTossDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import com.sparta.orderservice.payment.application.exception.PaymentExceptionCode;
import com.sparta.orderservice.payment.application.usercase.PaymentUseCase;
import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import com.sparta.orderservice.payment.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.payment.infrastructure.feign.TossPaymentsFeignClientApi;

import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final TossPaymentsFeignClientApi tossPaymentsFeignClientApi;
    private final PaymentRepository paymentRepository;
    private final OrderFacadeV1 orderFacadeV1;
    private final ProductFeignClientApiV1 productFeignClientApiV1;

    @Override
    public void createPayment(ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1){

        ResPaymentTossDto tossRes = tossPaymentsFeignClientApi.confirmPayment(ReqPaymentTossDto
                .of(reqPaymentPostDtoApiV1.getPayment().getOrderId()
                        , reqPaymentPostDtoApiV1.getPayment().getAmount()
                        , reqPaymentPostDtoApiV1.getPayment().getPaymentKey()
                )
        );

        //결제 실패
        if (tossRes.getFailure() != null) {
            productFeignClientApiV1.postRestoreById(reqPaymentPostDtoApiV1.getPayment().productId);
            throw new CustomException(PaymentExceptionCode.PAYMENT_FAILURE);
        }

        // 결제 객체 저장
        PaymentEntity paymentEntity = PaymentEntity.createPayment(tossRes);
        paymentRepository.save(paymentEntity);


        ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1 = ReqOrderPutDtoApiV1.builder()
                .order(ReqOrderPutDtoApiV1.Order.builder()
                        .paymentStatus(PaymentStatus.PAID)
                        .paymentId(paymentEntity.getPaymentId())
                        .build())
                .build();


        // 주문 객체 수정 -> 결제상태 / 결제 ID
        orderFacadeV1.updateBy(reqOrderPutDtoApiV1, reqPaymentPostDtoApiV1.getPayment().getOrderId());
    }

    public PaymentEntity getBy(UUID paymentId) {
        return paymentRepository.findById(paymentId);
    }
}

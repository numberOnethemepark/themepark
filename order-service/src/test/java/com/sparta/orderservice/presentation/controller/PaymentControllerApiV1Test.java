package com.sparta.orderservice.presentation.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.payment.application.dto.request.ReqPaymentTossDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import com.sparta.orderservice.payment.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.payment.infrastructure.feign.TossPaymentsFeignClientApi;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.UUID;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
public class PaymentControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockitoBean
    private TossPaymentsFeignClientApi tossPaymentsFeignClientApi;

    @MockitoBean
    private ProductFeignClientApiV1 productFeignClientApiV1;

    @Autowired
    private OrderRepository orderRepository;

    private OrderEntity orderEntity;

    private PaymentEntity paymentEntity;
    private ResPaymentTossDto resPaymentTossDto;

    @BeforeEach
    void setUp() {
        resPaymentTossDto = ResPaymentTossDto.builder()
                .paymentKey("payment-key")
                .orderId(UUID.fromString("b3a1c2d4-9f0e-4a8d-8b7f-123456789abc"))
                .status("DONE")
                .card(ResPaymentTossDto.Card.builder()
                        .amount(1000)
                        .number("card number")
                        .build())
                .failure(null)
                .build();

        paymentEntity = PaymentEntity.createPayment(resPaymentTossDto);
        paymentRepository.save(paymentEntity);

        ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1 = ReqOrdersPostDtoApiV1.builder()
                .order(
                        ReqOrdersPostDtoApiV1.Order.builder()
                                .slackId("TestSlackId")
                                .productId(UUID.fromString("b3a1c2d4-9f0e-4a8d-8b7f-123456789abc"))
                                .amount(10000)
                                .build()
                )
                .build();

        orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV1.getOrder().getProductId(),reqOrdersPostDtoApiV1.getOrder().getAmount(), reqOrdersPostDtoApiV1.getOrder().getSlackId(),1L);
        orderRepository.save(orderEntity);
    }

    @Test
    public void testPaymentGetSuccess() throws Exception {
        // given
        UUID paymentId = paymentEntity.getPaymentId();

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/payments/{id}", paymentId)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("결제 조회 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Payment v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("결제 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testPaymentPostSuccess() throws Exception {
        // given
        UUID productId = UUID.randomUUID();

        ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1 = ReqPaymentPostDtoApiV1.builder()
                .payment(ReqPaymentPostDtoApiV1.Payment.builder()
                        .amount(100)
                        .orderId(orderEntity.getOrderId())
                        .productId(productId)
                        .paymentKey("payment-key")
                        .build())
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqPaymentPostDtoApiV1);

        when(tossPaymentsFeignClientApi.confirmPayment(any(ReqPaymentTossDto.class))).thenReturn(resPaymentTossDto);

        doNothing().when(productFeignClientApiV1).postRestoreById(productId);

        // when & then
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/payments")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isCreated()
        ).andDo(
                MockMvcRestDocumentationWrapper.document("결제 생성 성공!",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Payment v1")
                                .requestFields(
                                        fieldWithPath("payment.orderId").type(JsonFieldType.STRING).description("주문 ID"),
                                        fieldWithPath("payment.amount").type(JsonFieldType.NUMBER).description("주문 금액"),
                                        fieldWithPath("payment.paymentKey").type(JsonFieldType.STRING).description("결제 키"),
                                        fieldWithPath("payment.productId").type(JsonFieldType.STRING).description("상품 ID")
                                )
                                .build()
                        )
                )
        );
    }
}

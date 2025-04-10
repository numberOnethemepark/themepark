package com.sparta.orderservice.presentation.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderservice.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.application.dto.request.ReqPaymentPutDtoApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.UUID;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("dev")
public class PaymentControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPaymentGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/payments/{id}", UUID.randomUUID())
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
        ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1 = ReqPaymentPostDtoApiV1.builder()
                .payment(ReqPaymentPostDtoApiV1.Payment.builder()
                        .amount(100)
                        .orderId(UUID.randomUUID())
                        .cardNumber("1234")
                        .build())
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqPaymentPostDtoApiV1);

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
                                        fieldWithPath("payment.cardNumber").type(JsonFieldType.STRING).description("카드 번호")
                                )
                                .build()
                        )
                )
        );

    }

    @Test
    public void testPaymentPutSuccess() throws Exception {

        ReqPaymentPutDtoApiV1 reqPaymentPutDtoApiV1 = ReqPaymentPutDtoApiV1.builder()
                .payment(ReqPaymentPutDtoApiV1.Payment.builder()
                        .paymentStatus("paid")
                        .build())
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqPaymentPutDtoApiV1);

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/v1/payments/{id}", UUID.randomUUID())
                            .content(reqDtoJson)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("결제 수정 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Payment v1")
                                        .requestFields(
                                                fieldWithPath("payment.paymentStatus").type(JsonFieldType.STRING).description("결제 상태")
                                        )
                                        .build()
                                )
                        )
                );
    }

}

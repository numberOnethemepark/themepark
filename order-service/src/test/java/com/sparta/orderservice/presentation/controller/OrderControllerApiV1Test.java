package com.sparta.orderservice.presentation.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
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

import java.util.UUID;


import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("dev")
public class OrderControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testOrderGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/orders/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("Order 개별 조회 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("주문 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testOrderGetSuccess2() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/orders")
                        .param("userId", UUID.randomUUID().toString())
                        .param("page", "10")
                        .param("size", "0")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("Order 서치 조회 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order v1")
                                        .queryParameters(
                                                parameterWithName("userId").type(SimpleType.STRING).description("사용자 ID (UUID 형식)"),
                                                parameterWithName("page").type(SimpleType.NUMBER).description("페이지 번호 (0부터 시작)"),
                                                parameterWithName("size").type(SimpleType.NUMBER).description("페이지 당 항목 수")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testOrderPostSuccess() throws Exception {

        ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1 = ReqOrdersPostDtoApiV1.builder()
                .order(
                ReqOrdersPostDtoApiV1.Order.builder()
                        .slackId("1234")
                        .orderQuantity(100)
                        .productId(UUID.randomUUID())
                        .build()
                )
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrdersPostDtoApiV1);

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/orders")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Order 생성 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order v1")
                                        .requestFields(
                                                fieldWithPath("order.slackId").type(JsonFieldType.STRING).description("주문자 슬랙 ID"),
                                                fieldWithPath("order.orderQuantity").type(JsonFieldType.NUMBER).description("주문 상태"),
                                                fieldWithPath("order.productId").type(JsonFieldType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testOrderPutSuccess() throws Exception {

        ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1 = ReqOrderPutDtoApiV1.builder()
                .order(ReqOrderPutDtoApiV1.Order.builder()
                        .slackId("1234")
                        .orderStatus("paid")
                        .build())
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrderPutDtoApiV1);

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/v1/orders/{id}", UUID.randomUUID())
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Order 수정 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order v1")
                                        .requestFields(
                                                fieldWithPath("order.slackId").type(JsonFieldType.STRING).description("주문자 슬랙 ID"),
                                                fieldWithPath("order.orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testOrderDeleteSuccess() throws Exception {

        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/orders/{id}", UUID.randomUUID())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Order 삭제 성공!",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("주문 ID")
                                        )
                                        .build()
                                )
                        )
                );

    }
}
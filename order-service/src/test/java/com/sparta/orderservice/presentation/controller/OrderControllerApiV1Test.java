package com.sparta.orderservice.presentation.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
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

import java.time.LocalDateTime;
import java.util.UUID;


import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("test")
public class OrderControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    private OrderEntity orderEntity;
    private ResDTO<ResProductGetByIdDTOApiV1> response;



    @MockitoBean
    private ProductFeignClientApiV1 productFeignClientApiV1;

    @BeforeEach
    void setUp(){
        ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1 = ReqOrdersPostDtoApiV1.builder()
                .order(
                        ReqOrdersPostDtoApiV1.Order.builder()
                                .slackId("TestSlackId")
                                .productId(UUID.fromString("b3a1c2d4-9f0e-4a8d-8b7f-123456789abc"))
                                .amount(10000)
                                .build()
                )
                .build();

        orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV1, 1L);
        orderRepository.save(orderEntity);

        ResProductGetByIdDTOApiV1 resProductGetByIdDTOApiV1 = ResProductGetByIdDTOApiV1.builder()
                .product(ResProductGetByIdDTOApiV1.Product.builder()
                        .name("할인 이용권")
                        .description("5월 한정")
                        .productStatus("EVENT")
                        .price(25000)
                        .limitQuantity(100)
                        .eventStartAt(LocalDateTime.parse("2025-05-01T09:00:00"))
                        .eventEndAt(LocalDateTime.parse("2025-05-31T23:59:59"))
                        .build())
                .build();

        response = ResDTO.<ResProductGetByIdDTOApiV1>builder()
                .code(0)
                .message("확인되었습니다.")
                .data(resProductGetByIdDTOApiV1)
                .build();
    }

    @Test
    public void testOrderGetSuccess() throws Exception {
        // given
        UUID orderId = orderEntity.getOrderId();

        // when & then
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/orders/{id}", orderId)
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
        // given
        Long userId = orderEntity.getUserId();

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/orders")
                        .param("userId", userId.toString())
                        .param("size", String.valueOf(10))
                        .param("page", String.valueOf(0))
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
                        .productId(UUID.randomUUID())
                        .amount(10000)
                        .build()
                )
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrdersPostDtoApiV1);

        when(productFeignClientApiV1.getBy(any())).thenReturn(response);

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/orders")
                        .header("X-User-Id", 1L)
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
                                                fieldWithPath("order.amount").type(JsonFieldType.NUMBER).description("주문 금액"),
                                                fieldWithPath("order.productId").type(JsonFieldType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }


    @Test
    public void testOrderPutSuccess() throws Exception {
        // given
        ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1 = ReqOrderPutDtoApiV1.builder()
                .order(ReqOrderPutDtoApiV1.Order.builder()
                        .slackId("1234")
                        .paymentStatus(PaymentStatus.NOT_PAID)
                        .paymentId(null)
                        .build())
                .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrderPutDtoApiV1);
        UUID orderId = orderEntity.getOrderId();

        // when & then
        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/v1/orders/{id}", orderId)
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
                                                fieldWithPath("order.paymentStatus").type(JsonFieldType.STRING).description("결제상태"),
                                                fieldWithPath("order.paymentId")
                                                        .type(JsonFieldType.STRING)
                                                        .optional()
                                                        .description("결제 ID (결제 되어있지 않다면 null)")
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
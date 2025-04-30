package com.business.productservice.product.presentation.controller.v3;

import com.business.productservice.application.dto.v3.request.ReqProductPostDTOApiV3;
import com.business.productservice.application.dto.v3.request.ReqProductPutDTOApiV3;
import com.business.productservice.application.dto.v3.request.ReqStockDecreasePostDTOApiV3;
import com.business.productservice.application.dto.v3.request.ReqStockRestorePostDTOApiV3;
import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class ProductControllerApiV3Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testProductPostSuccess() throws Exception {
        ReqProductPostDTOApiV3 dto = ReqProductPostDTOApiV3.builder()
                .product(
                        ReqProductPostDTOApiV3.Product.builder()
                                .name("20% 할인권")
                                .description("할인권 설명입니다.")
                                .productType(ProductType.EVENT)
                                .price(30000)
                                .limitQuantity(100)
                                .eventStartAt(LocalDateTime.now())
                                .eventEndAt(LocalDateTime.now().plusDays(1))
                                .build()
                )
                .build();

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v3/products")
                        .header("X-User-Role","MASTER")
                        .content(dtoToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 생성 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testProductGetByIdSuccess() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v3/products/{id}", "476c2d9e-cb99-49a3-9fb8-721096683d04")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 개별 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testProductGetSuccess() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v3/products")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 전체 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testProductPutByIdSuccess() throws Exception {
        ReqProductPutDTOApiV3 dto = ReqProductPutDTOApiV3.builder()
                .product(
                        ReqProductPutDTOApiV3.Product.builder()
                                .name("상품 이름")
                                .description("상품 설명")
                                .productType(ProductType.EVENT)
                                .price(35000)
                                .limitQuantity(100)
                                .eventStartAt(LocalDateTime.now())
                                .eventEndAt(LocalDateTime.now().plusDays(1))
                                .productStatus(ProductStatus.CLOSED)
                                .build()
                )
                .build();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.put("/v3/products/{id}",UUID.randomUUID())
                        .content(dtoToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );

    }

    @Test
    public void testProductDeleteSuccess() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/v3/products/{id}", UUID.randomUUID())
                                .header("X-User-Role","MASTER")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testStockDecreasePostSuccess() throws Exception {

        ReqStockDecreasePostDTOApiV3 dto = ReqStockDecreasePostDTOApiV3.builder()
                .stock(
                        ReqStockDecreasePostDTOApiV3.Stock.builder()
                                .stockDecreaseAmount(3)
                                .build()
                )
                .build();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v3/products/internal/{id}/stocks-decrease", UUID.randomUUID())
                                .header("X-User-Role","MASTER")
                                .content(dtoToJson(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품 재고 차감 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testStockRestorePostSuccess() throws Exception {

        ReqStockRestorePostDTOApiV3 dto = ReqStockRestorePostDTOApiV3.builder()
                .stock(
                        ReqStockRestorePostDTOApiV3.Stock.builder()
                                .stockRestoreAmount(3)
                                .build()
                )
                .build();
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v3/products/internal/{id}/stocks-restore", UUID.randomUUID())
                                .header("X-User-Role","MASTER")
                                .content(dtoToJson(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpectAll(
                                MockMvcResultMatchers.status().isOk()
                        )
                        .andDo(
                                MockMvcRestDocumentationWrapper.document("상품 재고 복구 성공",
                                        preprocessRequest(prettyPrint()),
                                        preprocessResponse(prettyPrint()),
                                        resource(ResourceSnippetParameters.builder()
                                                .tag("Product v3")
                                                .pathParameters(
                                                        parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                                )
                                                .build()
                                        )
                                )
                        );

    }

    @Test
    public void testStockGetByIdSuccess() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v3/products/{id}/stock", "476c2d9e-cb99-49a3-9fb8-721096683d04")
                                .header("X-User-Role","MASTER")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("상품재고 개별 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Product v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("상품 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    private String dtoToJson(Object dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }
}

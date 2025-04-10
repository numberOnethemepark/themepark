package com.business.productservice.product.presentation.controller;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockDecreasePostDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockRestorePostDTOApiV1;
import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class ProductControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testProductPostSuccess() throws Exception {
        ReqProductPostDTOApiV1 dto = ReqProductPostDTOApiV1.builder()
                .product(
                        ReqProductPostDTOApiV1.Product.builder()
                                .name("20% 할인권")
                                .description("할인권 설명입니다.")
                                .productType(ProductType.EVENT)
                                .price(30000L)
                                .limitQuantity(100)
                                .eventStartAt(LocalDateTime.now())
                                .eventEndAt(LocalDateTime.MAX)
                                .build()
                )
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/products")
                        .content(dtoToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    public void testProductGetByIdSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/products/{id}", UUID.randomUUID())
        )
        .andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testProductGetSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/products")
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testProductPutByIdSuccess() throws Exception {
        ReqProductPutDTOApiV1 dto = ReqProductPutDTOApiV1.builder()
                .product(
                        ReqProductPutDTOApiV1.Product.builder()
                                .name("상품 이름")
                                .description("상품 설명")
                                .productType(ProductType.EVENT)
                                .price(35000L)
                                .limitQuantity(100)
                                .eventStartAt(LocalDateTime.now())
                                .eventEndAt(LocalDateTime.MAX)
                                .status(ProductStatus.CLOSED)
                                .build()
                )
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/products/{id}",UUID.randomUUID())
                        .content(dtoToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );

    }

    @Test
    public void testProductDeleteSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/products/{id}", UUID.randomUUID())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testStockDecreasePostSuccess() throws Exception {

        ReqStockDecreasePostDTOApiV1 dto = ReqStockDecreasePostDTOApiV1.builder()
                .stock(
                        ReqStockDecreasePostDTOApiV1.Stock.builder()
                                .stockDecreaseAmount(3)
                                .build()
                )
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/products/{id}/stocks-decrease", UUID.randomUUID())
                                .content(dtoToJson(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testStockRestorePostSuccess() throws Exception {

        ReqStockRestorePostDTOApiV1 dto = ReqStockRestorePostDTOApiV1.builder()
                .stock(
                        ReqStockRestorePostDTOApiV1.Stock.builder()
                                .stockRestoreAmount(3)
                                .build()
                )
                .build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/products/{id}/stocks-restore", UUID.randomUUID())
                        .content(dtoToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    private String dtoToJson(Object dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }
}

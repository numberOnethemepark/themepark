package com.sparta.orderservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
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

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
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
                MockMvcRequestBuilders.get("/v1/orders/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testOrderGetSuccess2() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/orders")
                        .param("userId", "1")
                        .param("page", "10")
                        .param("size", "0")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testOrderPostSuccess() throws Exception {

        ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1 = ReqOrderPutDtoApiV1.builder().build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrderPutDtoApiV1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/orders")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    public void testOrderPutSuccess() throws Exception {

        ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1 = ReqOrderPutDtoApiV1.builder().build();

        String reqDtoJson = objectMapper.writeValueAsString(reqOrderPutDtoApiV1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/orders/{id}", UUID.randomUUID())
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testOrderDeleteSuccess() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/orders/{id}", UUID.randomUUID())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );

    }
}
package com.sparta.orderservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderservice.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.application.dto.request.ReqPaymentPutDtoApiV1;
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
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class PaymentControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPaymentGetSuccess() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/payments/{id}", UUID.randomUUID())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testPaymentPostSuccess() throws Exception {
        ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1 = ReqPaymentPostDtoApiV1.builder().build();

        String reqDtoJson = objectMapper.writeValueAsString(reqPaymentPostDtoApiV1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/payments")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testPaymentPutSuccess() throws Exception {

        ReqPaymentPutDtoApiV1 reqPaymentPutDtoApiV1 = ReqPaymentPutDtoApiV1.builder().build();

        String reqDtoJson = objectMapper.writeValueAsString(reqPaymentPutDtoApiV1);

        mockMvc.perform(
                    MockMvcRequestBuilders.put("/v1/payments/{id}", UUID.randomUUID())
                            .content(reqDtoJson)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

}

package com.business.themeparkservice.waiting.presentation.controller;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
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

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class WaitingControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testWaitingPostSuccess() throws Exception{
        ReqWaitingPostDTOApiV1 reqWaitingPostDTOApiV1 =
                ReqWaitingPostDTOApiV1.builder().waiting(
                        ReqWaitingPostDTOApiV1.Waiting.builder()
                                .themeparkId(UUID.randomUUID())
                                .userId(2)
                                .build()
                ).build();

        String reqJsonDto = objectMapper.writeValueAsString(reqWaitingPostDTOApiV1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/waitings")
                        .content(reqJsonDto)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    public void testWaitingGetByIdSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/waitings/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testWaitingGetSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/waitings")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testWaitingPostDoneSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/waitings/{id}", UUID.randomUUID()+"/done")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testWaitingPostCancelSuccess() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/waitings/{id}", UUID.randomUUID()+"/cancel")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testWaitingGetDeletedSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/waitings/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }
}

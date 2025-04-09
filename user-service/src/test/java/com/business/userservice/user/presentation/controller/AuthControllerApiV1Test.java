package com.business.userservice.user.presentation.controller;

import com.business.userservice.application.dto.request.ReqAuthPostGuestLoginDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class AuthControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthPostJoinSuccess() throws Exception {

        ReqAuthPostJoinDTOApiV1 dto = ReqAuthPostJoinDTOApiV1.builder()
            .user(
                ReqAuthPostJoinDTOApiV1.User.builder()
                    .username("test")
                    .password("password1234")
                    .slackId("010xxxx1111")
                    .build()
            )
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/auth/join")
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testAuthPostLoginSuccess() throws Exception {

        ReqAuthPostLoginDTOApiV1 dto = ReqAuthPostLoginDTOApiV1.builder()
            .user(
                ReqAuthPostLoginDTOApiV1.User.builder()
                    .username("test")
                    .password("password1234")
                    .build()
            )
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/auth/login")
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testAuthPostGuestLoginSuccess() throws Exception {
        ReqAuthPostGuestLoginDTOApiV1 dto = ReqAuthPostGuestLoginDTOApiV1.builder()
            .user(
                ReqAuthPostGuestLoginDTOApiV1.User.builder()
                    .slackId("010xxxx1234")
                    .build()
            )
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/auth/guest-login")
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

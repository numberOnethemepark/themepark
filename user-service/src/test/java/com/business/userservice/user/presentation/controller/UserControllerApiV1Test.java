package com.business.userservice.user.presentation.controller;

import com.business.userservice.application.dto.request.ReqUserDeleteDTOApiV1;
import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
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
public class UserControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserGetByIdSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/users/{id}", 1)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testUserPutSuccess() throws Exception {

        ReqUserPutDTOApiV1 dto = ReqUserPutDTOApiV1.builder()
            .user(
                ReqUserPutDTOApiV1.User.builder()
                    .username("test")
                    .password("password1234")
                    .slackId("slack-id")
                    .build()
            )
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/users/{id}", 1)
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testUserGetSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/users")
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testUserPostDeleteByIdSuccess() throws Exception {
        ReqUserDeleteDTOApiV1 dto = ReqUserDeleteDTOApiV1.builder()
            .user(
                ReqUserDeleteDTOApiV1.User.builder()
                    .password("password1234")
                    .build()
            )
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/users/{id}/delete", 1)
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    @Test
    public void testUserPatchSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/v1/users/{id}/is-blacklisted", 1)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            );
    }

    private String dtoToJson(Object dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }
}

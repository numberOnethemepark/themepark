package com.business.themeparkservice.hashtag.presentation;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
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
public class HashtagControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThemeparkPostSuccess() throws Exception{
        ReqHashtagPostDTOApiV1 reqHashtagPostDTOApiV1 =
                ReqHashtagPostDTOApiV1.builder().hashtag(
                                ReqHashtagPostDTOApiV1.Hashtag.builder()
                                        .name("신나는")
                                        .build()
                        ).build();

        String reqDtoJson = objectMapper.writeValueAsString(reqHashtagPostDTOApiV1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/hashtags")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    public void testThemeparkGetByIdSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/hashtags/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testThemeparkGetSuccess() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/hashtags")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testThemeparkPutSuccess() throws Exception{
        ReqHashtagPutDTOApiV1 reqHashtagPutDTOApiV1 =
                ReqHashtagPutDTOApiV1.builder().hashtag(
                        ReqHashtagPutDTOApiV1.Hashtag.builder()
                                .name("신나는")
                                .build()
                ).build();

        String reqDtoJson = objectMapper.writeValueAsString(reqHashtagPutDTOApiV1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/hashtags/{id}", UUID.randomUUID())
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testThemeparkDeleteSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/hashtags/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                );
    }
}

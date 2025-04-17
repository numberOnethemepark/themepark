package com.business.themeparkservice.themepark.presentation.controller;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
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

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("dev")
public class ThemeparkControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThemeparkPostSuccess() throws Exception{


        List<ReqThemeparkPostDTOApiV1.ThemePark.Hashtag> hashtags = Arrays.asList(
                ReqThemeparkPostDTOApiV1.ThemePark.Hashtag.builder()
                        .hashtagId(UUID.fromString("314202c4-ae6a-4d0b-997d-95057b520748"))
                        .name("신나는").build(),
                ReqThemeparkPostDTOApiV1.ThemePark.Hashtag.builder()
                        .hashtagId(UUID.fromString("58d75bbe-61cc-46eb-8419-d0ef49a952fe"))
                        .name("즐거운").build()
        );

        ReqThemeparkPostDTOApiV1 reqThemeparkPostDTOApiV1 =
                ReqThemeparkPostDTOApiV1.builder().themepark(
                                ReqThemeparkPostDTOApiV1.ThemePark.builder()
                                        .name("놀이기구2")
                                        .description("슝슝 놀이기구입니다.3")
                                        .type(ThemeparkType.valueOf("RIDE"))
                                        .operationStartTime(LocalTime.parse("10:00"))
                                        .operationEndTime(LocalTime.parse("18:00"))
                                        .heightLimit("130~180cm")
                                        .supervisor(true)
                                        .hashtagList(hashtags)
                                        .build()
                        )
                        .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqThemeparkPostDTOApiV1);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/themeparks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-User-Id",1)
                                .content(reqDtoJson)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("Themepark 생성 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Themepark v1")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkGetByIdSuccess() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/themeparks/{id}", "0ad6129e-540c-45da-b389-34d69114cd95")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("Themepark 개별 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Themepark v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkGetSuccess() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/themeparks")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
                .andDo(
                        MockMvcRestDocumentationWrapper.document("Themepark 전체 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Themepark v1")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkUpdateSuccess() throws Exception{

        ReqThemeparkPutDTOApiV1 reqThemeparkPutDTOApiV1 =
                ReqThemeparkPutDTOApiV1.builder().themepark(
                                ReqThemeparkPutDTOApiV1.ThemePark.builder()
                                        .heightLimit("130~180cm")
                                        .build()
                        )
                        .build();

        String reqDtoJson = objectMapper.writeValueAsString(reqThemeparkPutDTOApiV1);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.put("/v1/themeparks/{id}", "0ad6129e-540c-45da-b389-34d69114cd95")
                            .header("X-User-Id",1)
                            .content(reqDtoJson)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
              .andDo(
                        MockMvcRestDocumentationWrapper.document("Themepark 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Themepark v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkDeleteSuccess() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/v1/themeparks/{id}", "0ad6129e-540c-45da-b389-34d69114cd95")
                        .header("X-User-Id",1)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                )
              .andDo(
                        MockMvcRestDocumentationWrapper.document("Themepark 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Themepark v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }


}

package com.business.themeparkservice.hashtag.presentation.controller;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
                                        .name("액션")
                                        .build()
                        ).build();

        String reqDtoJson = objectMapper.writeValueAsString(reqHashtagPostDTOApiV1);
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/hashtags")
                        .header("X-User-Id",1)
                        .header("X-User-Role","MASTER")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Hashtag 생성 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Hashtag v1")
                                        .requestHeaders(
                                                headerWithName("X-User-Id").description("회원 id")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkGetByIdSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/hashtags/{id}", "376df3fd-f172-4046-8bd2-9ab22b9c79cd")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Hashtag 단일 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Hashtag v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("해시태그 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkGetSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/hashtags")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Hashtag 전체 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Hashtag v1")
                                        .build()
                                )
                        )
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
                RestDocumentationRequestBuilders.put("/v1/hashtags/{id}", "376df3fd-f172-4046-8bd2-9ab22b9c79cd")
                        .header("X-User-Id",1)
                        .header("X-User-Role","MASTER")
                        .content(reqDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Hashtag 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Hashtag v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("해시태그 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testThemeparkDeleteSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/hashtags/{id}", "376df3fd-f172-4046-8bd2-9ab22b9c79cd")
                        .header("X-User-Id",1)
                        .header("X-User-Role","MASTER")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Hashtag 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Hashtag v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("해시태그 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }
}

package com.business.themeparkservice.waiting.presentation.controller;

import com.business.themeparkservice.waiting.application.dto.request.v2.ReqWaitingPostDTOApiV2;
import com.business.themeparkservice.waiting.application.dto.request.v3.ReqWaitingPostDTOApiV3;
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

import java.util.UUID;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("dev")
public class WaitingControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testWaitingPostSuccess() throws Exception{
        ReqWaitingPostDTOApiV3 reqWaitingPostDTOApiV3 =
                ReqWaitingPostDTOApiV3.builder().waiting(
                        ReqWaitingPostDTOApiV3.Waiting.builder()
                                .themeparkId(UUID.fromString("0ad6129e-540c-45da-b389-34d69114cd95"))
                                .build()
                ).build();

        String reqJsonDto = objectMapper.writeValueAsString(reqWaitingPostDTOApiV3);

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v3/waitings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", 1)
                        .header("X-User-Role","USER")
                        .content(reqJsonDto)
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 생성 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testWaitingGetByIdSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v3/waitings/{id}", UUID.randomUUID())
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 단일 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("대기 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testWaitingGetSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v3/waitings")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 전체 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testWaitingPostDoneSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v3/waitings/{id}/done", UUID.randomUUID())
                        .header("X-User-Role","MASTER")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 완료 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("대기 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testWaitingPostCancelSuccess() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v3/waitings/{id}/cancel", UUID.randomUUID())
                        .header("X-User-Role","MASTER")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 취소 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("대기 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testWaitingGetDeletedSuccess() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v3/waitings/{id}", UUID.randomUUID())
                        .header("X-User-Id", 1)
                        .header("X-User-Role","MASTER")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk()
                ).andDo(
                        MockMvcRestDocumentationWrapper.document("Waiting 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Waiting v3")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.STRING).description("대기 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }
}

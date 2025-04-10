package com.business.userservice.user.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.business.userservice.application.dto.request.ReqUserDeleteDTOApiV1;
import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
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

@SpringBootTest
@AutoConfigureRestDocs
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
                RestDocumentationRequestBuilders.get("/v1/users/{id}", 1)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("USER v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.NUMBER).description("유저 ID")
                        )
                        .build()
                    )
                )
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
                RestDocumentationRequestBuilders.put("/v1/users/{id}", 1)
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 정보 수정 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("USER v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.NUMBER).description("유저 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testUserGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/users")
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 전체 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("USER v1")
                        .build()
                    )
                )
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
                RestDocumentationRequestBuilders.post("/v1/users/{id}/delete", 1)
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 삭제 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("USER v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.NUMBER).description("유저 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testUserPatchSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/v1/users/{id}/is-blacklisted", 1)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 블랙리스트 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("USER v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.NUMBER).description("유저 ID")
                        )
                        .build()
                    )
                )
            );
    }

    private String dtoToJson(Object dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }
}

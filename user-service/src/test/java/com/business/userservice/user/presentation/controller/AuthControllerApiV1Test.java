package com.business.userservice.user.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1.User;
import com.business.userservice.domain.user.vo.RoleType;
import com.business.userservice.infrastructure.jwt.JwtUtil;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
public class AuthControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private JwtUtil jwtUtil;

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
                RestDocumentationRequestBuilders.post("/v1/auth/join")
                    .content(dtoToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("회원 가입 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("AUTH v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testAuthPostLoginSuccess() throws Exception {
        ReqAuthPostJoinDTOApiV1 dto = ReqAuthPostJoinDTOApiV1.builder()
            .user(
                ReqAuthPostJoinDTOApiV1.User.builder()
                    .username("test1111")
                    .password("password1234")
                    .slackId("010xxxx1111")
                    .build()
            )
            .build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/auth/join")
                .content(dtoToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
        );

        ReqAuthPostLoginDTOApiV1 loginDto = new ReqAuthPostLoginDTOApiV1(
            new User("test1111", "password1234")
        );

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/auth/login")
                    .content(dtoToJson(loginDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("로그인 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("AUTH v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testAuthGetRefreshSuccess() throws Exception {
        String accessJwt = jwtUtil.createToken("ACCESS", 1L, RoleType.USER, 0L);
        String refreshJwt = jwtUtil.createRefreshToken(accessJwt);

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/auth/refresh?accessToken=" + accessJwt + "&refreshToken=" + refreshJwt)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("토큰 재발급 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("AUTH v1")
                        .build()
                    )
                )
            );
    }

    private String dtoToJson(Object dto) throws Exception {
        return objectMapper.writeValueAsString(dto);
    }
}


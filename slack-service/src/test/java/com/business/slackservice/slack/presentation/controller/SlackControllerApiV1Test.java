package com.business.slackservice.slack.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.business.slackservice.application.dto.request.slack.ReqSlackPostDTOApiV1;
import com.business.slackservice.application.dto.request.slack.ReqSlackPostDTOApiV1.Slack;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
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
public class SlackControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSlackPostSuccess() throws Exception {
        ReqSlackPostDTOApiV1 req = ReqSlackPostDTOApiV1.builder()
            .slack(
                ReqSlackPostDTOApiV1.Slack.builder()
                    .slackEventType("Slack-Event-Type")
                    .relatedName("Slack-Related-Name")
                    .slackEventType("USER_DM")
                    .target(
                        Slack.SlackTarget.builder()
                            .slackId("Slack-Id")
                            .type(TargetType.USER_DM)
                            .build()
                    )
                    .build()
            )
            .build();
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/internal/slacks")
                    .content(dtoToJson(req))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 생성 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackGetByIdSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slacks/{id}", UUID.randomUUID())
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 단건 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slacks/{id}", UUID.randomUUID())
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 삭제 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackDeleteByIdSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/slacks/{id}", UUID.randomUUID())
                    .header("X-User-Id", 1L)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 템플릿 삭제 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 ID")
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

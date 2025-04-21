package com.business.slackservice.slack.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePutDTOApiV1;
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
public class SlackEventTypeControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSlackEventTypePostSuccess() throws Exception {
        ReqSlackEventTypePostDTOApiV1 req = ReqSlackEventTypePostDTOApiV1.builder()
            .slackEventType(
                ReqSlackEventTypePostDTOApiV1.SlackEventType.builder()
                    .name("TEST_EVENT_TYPE")
                    .build()
            )
            .build();

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/slack-event-types")
                    .content(dtoToJson(req))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 이벤트 타입 생성 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK EVENT TYPE v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackEventTypeGetByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slack-event-types/{id}", id)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 이벤트 타입 단건 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK EVENT TYPE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 이벤트 타입 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackEventTypeGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slack-event-types")
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 이벤트 타입 목록 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK EVENT TYPE v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackEventTypePutByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        ReqSlackEventTypePutDTOApiV1 req = ReqSlackEventTypePutDTOApiV1.builder()
            .slackEventType(
                ReqSlackEventTypePutDTOApiV1.SlackEventType.builder()
                    .name("TEST_EVENT_TYPE_UPDATE")
                    .build()
            )
            .build();

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/v1/slack-event-types/{id}", id)
                    .content(dtoToJson(req))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 이벤트 타입 수정 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK EVENT TYPE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 이벤트 타입 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackEventTypeDeleteByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/slack-event-types/{id}", id)
                    .header("X-User-Id", 1L)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 이벤트 타입 삭제 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK EVENT TYPE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 이벤트 타입 ID")
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

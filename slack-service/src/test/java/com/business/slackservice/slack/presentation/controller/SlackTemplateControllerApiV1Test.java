package com.business.slackservice.slack.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.business.slackservice.application.dto.request.slackTemplate.ReqSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackTemplate.ReqSlackTemplatePutDTOApiV1;
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
public class SlackTemplateControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSlackTemplatePostSuccess() throws Exception {
        ReqSlackTemplatePostDTOApiV1 req = ReqSlackTemplatePostDTOApiV1.builder()
            .slackTemplate(
                ReqSlackTemplatePostDTOApiV1.SlackTemplate.builder()
                    .slackEventTypeId(UUID.randomUUID())
                    .content("{{relatedName}} 주문이 정상적으로 접수되었습니다.}")
                    .build()
            )
            .build();
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/slack-templates")
                    .content(dtoToJson(req))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 템플릿 생성 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK TEMPLATE v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackTemplateGetByIdSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slack-templates/{id}", UUID.randomUUID())
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 템플릿 단건 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK TEMPLATE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 템플릿 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackTemplateGetSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/slack-templates")
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 템플릿 목록 조회 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK TEMPLATE v1")
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackTemplatePutByIdSuccess() throws Exception {
        ReqSlackTemplatePutDTOApiV1 req = ReqSlackTemplatePutDTOApiV1.builder()
            .slackTemplate(
                ReqSlackTemplatePutDTOApiV1.SlackTemplate.builder()
                    .SlackEventTypeId(UUID.randomUUID())
                    .content("{{relatedName}} 주문이 정상적으로 접수되었습니다. 업데이트")
                    .build()
            )
            .build();

        mockMvc.perform(
                RestDocumentationRequestBuilders.put("/v1/slack-templates/{id}", UUID.randomUUID())
                    .content(dtoToJson(req))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk()
            )
            .andDo(
                MockMvcRestDocumentationWrapper.document("슬랙 템플릿 수정 성공",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("SLACK TEMPLATE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 템플릿 ID")
                        )
                        .build()
                    )
                )
            );
    }

    @Test
    public void testSlackTemplateDeleteByIdSuccess() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/v1/slack-templates/{id}", UUID.randomUUID())
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
                        .tag("SLACK TEMPLATE v1")
                        .pathParameters(
                            parameterWithName("id").type(SimpleType.STRING)
                                .description("슬랙 템플릿 ID")
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

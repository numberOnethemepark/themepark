//package com.business.themeparkservice.themepark.presentation.controller;
//
//import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
//import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
//import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
//import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.epages.restdocs.apispec.SimpleType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static com.epages.restdocs.apispec.ResourceDocumentation.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Transactional
//@ActiveProfiles("dev")
//public class ThemeparkControllerApiV1Test {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testThemeparkPostSuccess() throws Exception{
//
//        List<ReqThemeparkPostDTOApiV1.ThemePark.Hashtag> hashtags = Arrays.asList(
//                ReqThemeparkPostDTOApiV1.ThemePark.Hashtag.builder().hashtagId(UUID.randomUUID()).name("신나는").build(),
//                ReqThemeparkPostDTOApiV1.ThemePark.Hashtag.builder().hashtagId(UUID.randomUUID()).name("즐거운").build()
//        );
//
//        ReqThemeparkPostDTOApiV1 reqThemeparkPostDTOApiV1 =
//                ReqThemeparkPostDTOApiV1.builder().themepark(
//                                ReqThemeparkPostDTOApiV1.ThemePark.builder()
//                                        .name("놀이기구1")
//                                        .description("슝슝 놀이기구입니다.")
//                                        .type(ThemeparkType.valueOf("RIDE"))
//                                        .operationStartTime(LocalTime.parse("10:00"))
//                                        .operationEndTime(LocalTime.parse("18:00"))
//                                        .heightLimit("130~180cm")
//                                        .supervisor(true)
//                                        .hashtagList(hashtags)
//                                        .build()
//                        )
//                        .build();
//
//        String reqDtoJson = objectMapper.writeValueAsString(reqThemeparkPostDTOApiV1);
//
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.post("/v1/themeparks")
//                                .content(reqDtoJson)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isCreated()
//                )
//                .andDo(
//                        MockMvcRestDocumentationWrapper.document("Themepark 생성 성공",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                resource(ResourceSnippetParameters.builder()
//                                        .tag("Themepark v1")
//                                        .build()
//                                )
//                        )
//                );
//    }
//
//    @Test
//    public void testThemeparkGetByIdSuccess() throws Exception{
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/v1/themeparks/{id}", UUID.randomUUID())
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isOk()
//                )
//                .andDo(
//                        MockMvcRestDocumentationWrapper.document("Themepark 개별 조회 성공",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                resource(ResourceSnippetParameters.builder()
//                                        .tag("Themepark v1")
//                                        .pathParameters(
//                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
//                                        )
//                                        .build()
//                                )
//                        )
//                );
//    }
//
//    @Test
//    public void testThemeparkGetSuccess() throws Exception{
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/v1/themeparks")
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isOk()
//                )
//                .andDo(
//                        MockMvcRestDocumentationWrapper.document("Themepark 전체 조회 성공",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                resource(ResourceSnippetParameters.builder()
//                                        .tag("Themepark v1")
//                                        .build()
//                                )
//                        )
//                );
//    }
//
//    @Test
//    public void testThemeparkUpdateSuccess() throws Exception{
//        List<ReqThemeparkPutDTOApiV1.ThemePark.Hashtag> hashtags = Arrays.asList(
//                ReqThemeparkPutDTOApiV1.ThemePark.Hashtag.builder().hashtagId(UUID.randomUUID()).name("즐거운").build(),
//                ReqThemeparkPutDTOApiV1.ThemePark.Hashtag.builder().hashtagId(UUID.randomUUID()).name("짜릿한").build()
//        );
//
//        ReqThemeparkPutDTOApiV1 reqThemeparkPutDTOApiV1 =
//                ReqThemeparkPutDTOApiV1.builder().themepark(
//                                ReqThemeparkPutDTOApiV1.ThemePark.builder()
//                                        .name("놀이기구1")
//                                        .description("슝슝 놀이기구입니다.")
//                                        .type(ThemeparkType.valueOf("RIDE"))
//                                        .operationStartTime(LocalTime.parse("10:00"))
//                                        .operationEndTime(LocalTime.parse("18:00"))
//                                        .heightLimit("140~180cm")
//                                        .supervisor(true)
//                                        .hashtagList(hashtags)
//                                        .build()
//                        )
//                        .build();
//
//        String reqDtoJson = objectMapper.writeValueAsString(reqThemeparkPutDTOApiV1);
//
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.put("/v1/themeparks/{id}", UUID.randomUUID())
//                            .content(reqDtoJson)
//                            .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isOk()
//                ).andDo(
//                        MockMvcRestDocumentationWrapper.document("Themepark 수정 성공",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                resource(ResourceSnippetParameters.builder()
//                                        .tag("Themepark v1")
//                                        .pathParameters(
//                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
//                                        )
//                                        .build()
//                                )
//                        )
//                );
//    }
//
//    @Test
//    public void testThemeparkDeleteSuccess() throws Exception{
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.delete("/v1/themeparks/{id}", UUID.randomUUID())
//                )
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isOk()
//                ).andDo(
//                        MockMvcRestDocumentationWrapper.document("Themepark 삭제 성공",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                resource(ResourceSnippetParameters.builder()
//                                        .tag("Themepark v1")
//                                        .pathParameters(
//                                                parameterWithName("id").type(SimpleType.STRING).description("테마파크 ID")
//                                        )
//                                        .build()
//                                )
//                        )
//                );
//    }
//
//
//}

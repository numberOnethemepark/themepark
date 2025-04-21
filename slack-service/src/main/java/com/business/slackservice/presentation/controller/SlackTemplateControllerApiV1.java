package com.business.slackservice.presentation.controller;

import com.business.slackservice.application.dto.request.slackTemplate.ReqSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackTemplate.ReqSlackTemplatePutDTOApiV1;
import com.business.slackservice.application.dto.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV1;
import com.business.slackservice.application.dto.response.slackTemplate.ResSlackTemplateGetDTOApiV1;
import com.business.slackservice.application.dto.response.slackTemplate.ResSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.service.SlackTemplateServiceApiV1;
import com.business.slackservice.application.service.SlackTemplateServiceImplApiV1;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/slack-templates")
public class SlackTemplateControllerApiV1 {

    private final SlackTemplateServiceApiV1 slackTemplateServiceApiV1;
    private final SlackTemplateServiceImplApiV1 slackTemplateServiceImplApiV1;

    @PostMapping
    public ResponseEntity<ResDTO<ResSlackTemplatePostDTOApiV1>> postBy(
        @Valid @RequestBody ReqSlackTemplatePostDTOApiV1 dto
    ) {
        ResSlackTemplatePostDTOApiV1 response = slackTemplateServiceApiV1.postBy(dto);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplatePostDTOApiV1>builder()
                .code(0)
                .message("슬랙 양식이 저장되었습니다.")
                .data(response)
                .build(),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResSlackTemplateGetByIdDTOApiV1>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackTemplateGetByIdDTOApiV1 response = slackTemplateServiceApiV1.getBy(id);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplateGetByIdDTOApiV1>builder()
                .code(0)
                .message("슬랙 양식 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResSlackTemplateGetDTOApiV1>> getBY(
        @QuerydslPredicate(root = SlackTemplateEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackTemplateGetDTOApiV1 response = slackTemplateServiceImplApiV1.getBy(predicate, pageable);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplateGetDTOApiV1>builder()
                .code(0)
                .message("슬랙 양식 목록 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
        @PathVariable UUID id,
        @Valid @RequestBody ReqSlackTemplatePutDTOApiV1 dto
    ) {
        slackTemplateServiceApiV1.putBy(id, dto);
        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("슬랙 양식 수정에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(
        @PathVariable UUID id,
        @RequestHeader("X-User-Id") Long userId
    ) {
        slackTemplateServiceApiV1.deleteBy(id, userId);
        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("슬랙 양식 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

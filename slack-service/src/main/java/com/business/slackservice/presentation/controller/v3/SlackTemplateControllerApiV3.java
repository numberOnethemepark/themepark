package com.business.slackservice.presentation.controller.v3;

import com.business.slackservice.application.dto.v3.request.slackTemplate.ReqSlackTemplatePostDTOApiV3;
import com.business.slackservice.application.dto.v3.request.slackTemplate.ReqSlackTemplatePutDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplateGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplatePostDTOApiV3;
import com.business.slackservice.application.service.v3.SlackTemplateServiceApiV3;
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
@RequestMapping("/v3/slack-templates")
public class SlackTemplateControllerApiV3 {

    private final SlackTemplateServiceApiV3 slackTemplateServiceApiV3;

    @PostMapping
    public ResponseEntity<ResDTO<ResSlackTemplatePostDTOApiV3>> postBy(
        @Valid @RequestBody ReqSlackTemplatePostDTOApiV3 dto
    ) {
        ResSlackTemplatePostDTOApiV3 response = slackTemplateServiceApiV3.postBy(dto);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplatePostDTOApiV3>builder()
                .code("0")
                .message("슬랙 양식이 저장되었습니다.")
                .data(response)
                .build(),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResSlackTemplateGetByIdDTOApiV3>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackTemplateGetByIdDTOApiV3 response = slackTemplateServiceApiV3.getBy(id);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplateGetByIdDTOApiV3>builder()
                .code("0")
                .message("슬랙 양식 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResSlackTemplateGetDTOApiV3>> getBY(
        @QuerydslPredicate(root = SlackTemplateEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackTemplateGetDTOApiV3 response = slackTemplateServiceApiV3.getBy(predicate, pageable);
        return new ResponseEntity<>(
            ResDTO.<ResSlackTemplateGetDTOApiV3>builder()
                .code("0")
                .message("슬랙 양식 목록 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
        @PathVariable UUID id,
        @Valid @RequestBody ReqSlackTemplatePutDTOApiV3 dto
    ) {
        slackTemplateServiceApiV3.putBy(id, dto);
        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
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
        slackTemplateServiceApiV3.deleteBy(id, userId);
        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message("슬랙 양식 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

package com.business.slackservice.presentation.controller;

import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePutDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.service.SlackEventTypeServiceApiV1;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
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
@RequestMapping("/v1/slack-event-types")
public class SlackEventTypeControllerApiV1 {

    private final SlackEventTypeServiceApiV1 slackEventTypeServiceApiV1;

    @PostMapping
    public ResponseEntity<ResDTO<ResSlackEventTypePostDTOApiV1>> postBy(
        @Valid @RequestBody ReqSlackEventTypePostDTOApiV1 dto
    ) {
        ResSlackEventTypePostDTOApiV1 response = slackEventTypeServiceApiV1.postBy(dto);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypePostDTOApiV1>builder()
                .code(0)
                .message("이벤트 타입이 저장되었습니다.")
                .data(response)
                .build(),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResSlackEventTypeGetByIdDTOApiV1>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackEventTypeGetByIdDTOApiV1 response = slackEventTypeServiceApiV1.getBy(id);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetByIdDTOApiV1>builder()
                .code(0)
                .message("이벤트 타입 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResSlackEventTypeGetDTOApiV1>> getBy(
        @QuerydslPredicate(root = SlackEventTypeEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackEventTypeGetDTOApiV1 response = slackEventTypeServiceApiV1.getBy(predicate, pageable);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetDTOApiV1>builder()
                .code(0)
                .message("이벤트 타입 목록 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
        @PathVariable UUID id,
        @Valid @RequestBody ReqSlackEventTypePutDTOApiV1 dto
    ) {
        slackEventTypeServiceApiV1.putBy(id, dto);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("이벤트 타입 수정에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(
        @PathVariable UUID id,
        @RequestHeader("X-User-Id") Long userId
    ) {
        slackEventTypeServiceApiV1.deleteBy(id, userId);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("이벤트 타입 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

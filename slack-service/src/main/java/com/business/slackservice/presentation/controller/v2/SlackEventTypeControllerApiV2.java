package com.business.slackservice.presentation.controller.v2;

import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePostDTOApiV2;
import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePutDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypePostDTOApiV2;
import com.business.slackservice.application.service.v2.SlackEventTypeServiceApiV2;
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
@RequestMapping("/v2/slack-event-types")
public class SlackEventTypeControllerApiV2 {

    private final SlackEventTypeServiceApiV2 slackEventTypeServiceApiV2;

    @PostMapping
    public ResponseEntity<ResDTO<ResSlackEventTypePostDTOApiV2>> postBy(
        @Valid @RequestBody ReqSlackEventTypePostDTOApiV2 dto
    ) {
        ResSlackEventTypePostDTOApiV2 response = slackEventTypeServiceApiV2.postBy(dto);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypePostDTOApiV2>builder()
                .code("0")
                .message("이벤트 타입이 저장되었습니다.")
                .data(response)
                .build(),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResSlackEventTypeGetByIdDTOApiV2>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackEventTypeGetByIdDTOApiV2 response = slackEventTypeServiceApiV2.getBy(id);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetByIdDTOApiV2>builder()
                .code("0")
                .message("이벤트 타입 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResSlackEventTypeGetDTOApiV2>> getBy(
        @QuerydslPredicate(root = SlackEventTypeEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackEventTypeGetDTOApiV2 response = slackEventTypeServiceApiV2.getBy(predicate, pageable);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetDTOApiV2>builder()
                .code("0")
                .message("이벤트 타입 목록 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
        @PathVariable UUID id,
        @Valid @RequestBody ReqSlackEventTypePutDTOApiV2 dto
    ) {
        slackEventTypeServiceApiV2.putBy(id, dto);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
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
        slackEventTypeServiceApiV2.deleteBy(id, userId);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message("이벤트 타입 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

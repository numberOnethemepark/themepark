package com.business.slackservice.presentation.controller.v3;

import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePostDTOApiV3;
import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePutDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypePostDTOApiV3;
import com.business.slackservice.application.service.v3.SlackEventTypeServiceApiV3;
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
@RequestMapping("/v3/slack-event-types")
public class SlackEventTypeControllerApiV3 {

    private final SlackEventTypeServiceApiV3 slackEventTypeServiceApiV3;

    @PostMapping
    public ResponseEntity<ResDTO<ResSlackEventTypePostDTOApiV3>> postBy(
        @Valid @RequestBody ReqSlackEventTypePostDTOApiV3 dto
    ) {
        ResSlackEventTypePostDTOApiV3 response = slackEventTypeServiceApiV3.postBy(dto);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypePostDTOApiV3>builder()
                .code("0")
                .message("이벤트 타입이 저장되었습니다.")
                .data(response)
                .build(),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResSlackEventTypeGetByIdDTOApiV3>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackEventTypeGetByIdDTOApiV3 response = slackEventTypeServiceApiV3.getBy(id);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetByIdDTOApiV3>builder()
                .code("0")
                .message("이벤트 타입 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResSlackEventTypeGetDTOApiV3>> getBy(
        @QuerydslPredicate(root = SlackEventTypeEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackEventTypeGetDTOApiV3 response = slackEventTypeServiceApiV3.getBy(predicate, pageable);

        return new ResponseEntity<>(
            ResDTO.<ResSlackEventTypeGetDTOApiV3>builder()
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
        @Valid @RequestBody ReqSlackEventTypePutDTOApiV3 dto
    ) {
        slackEventTypeServiceApiV3.putBy(id, dto);

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
        slackEventTypeServiceApiV3.deleteBy(id, userId);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message("이벤트 타입 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

package com.business.slackservice.presentation.controller.v1;

import com.business.slackservice.application.dto.v1.request.slack.ReqSlackPostDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackGetByIdDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackGetDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackPostDTOApiV1;
import com.business.slackservice.application.service.v1.SlackServiceApiV1;
import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SlackControllerApiV1 {

    private final SlackServiceApiV1 slackServiceApiV1;

    @PostMapping("/internal/slacks")
    public ResponseEntity<ResDTO<ResSlackPostDTOApiV1>> postBy(
        @RequestBody ReqSlackPostDTOApiV1 dto
    ) throws SlackApiException, IOException {
        ResSlackPostDTOApiV1 response = slackServiceApiV1.postBy(dto);
        return new ResponseEntity<>(
            ResDTO.<ResSlackPostDTOApiV1>builder()
                .code("0")
                .message("슬랙이 전송되었습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/slacks/{id}")
    public ResponseEntity<ResDTO<ResSlackGetByIdDTOApiV1>> getBy(
        @PathVariable UUID id
    ) {
        ResSlackGetByIdDTOApiV1 response = slackServiceApiV1.getBy(id);
        return new ResponseEntity<>(
            ResDTO.<ResSlackGetByIdDTOApiV1>builder()
                .code("0")
                .message("슬랙 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping("/slacks")
    public ResponseEntity<ResDTO<ResSlackGetDTOApiV1>> getBy(
        @QuerydslPredicate(root = SlackEntity.class) Predicate predicate,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        ResSlackGetDTOApiV1 response = slackServiceApiV1.getBy(predicate, pageable);
        return new ResponseEntity<>(
            ResDTO.<ResSlackGetDTOApiV1>builder()
                .code("0")
                .message("슬랙 목록 조회에 성공했습니다.")
                .data(response)
                .build(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/slacks/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(
        @PathVariable UUID id,
        @RequestHeader("X-User-Id") Long userId
    ) {
        slackServiceApiV1.deleteBy(id, userId);
        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message("슬랙 삭제에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

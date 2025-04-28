package com.business.themeparkservice.waiting.presentation.controller.v3;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.waiting.application.dto.request.v3.ReqWaitingPostDTOApiV3;
import com.business.themeparkservice.waiting.application.dto.response.v3.*;
import com.business.themeparkservice.waiting.application.service.v3.WaitingServiceApiV3;
import com.business.themeparkservice.waiting.infastructure.kafka.service.SlackKafkaServiceApiV1;
import com.github.themepark.common.application.aop.annotation.ApiPermission;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v3/waitings")
@RequiredArgsConstructor
public class WaitingControllerApiV3 {

    private final WaitingServiceApiV3 waitingService;

    private final SlackKafkaServiceApiV1 slackKafkaServiceApiV1;

    @ApiPermission(roles = {ApiPermission.Role.USER})
    @PostMapping
    public ResponseEntity<ResDTO<ResWaitingPostDTOApiV3>> postBy(
            @Valid @RequestBody ReqWaitingPostDTOApiV3 reqDto, @RequestHeader("X-User-Id")Long userId){

        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDTOApiV3>builder()
                        .code("0")
                        .message("대기열 생성을 성공했습니다.")
                        .data(waitingService.postBy(reqDto,userId))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResWaitingGetByIdDTOApiV3>> getBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetByIdDTOApiV3>builder()
                        .code("0")
                        .message("대기열 조회에 성공했습니다.")
                        .data(waitingService.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResWaitingGetDTOApiV3>> getBy(
            @QuerydslPredicate(root = ThemeparkEntity.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable)
    {

        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetDTOApiV3>builder()
                        .code("0")
                        .message("대기정보 검색에 성공했습니다.")
                        .data(waitingService.getBy(predicate,pageable))
                        .build(),
                HttpStatus.OK
        );
    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PostMapping("/{id}/done")
    public ResponseEntity<ResDTO<ResWaitingPostDoneDTOApiV3>> postDoneBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDoneDTOApiV3>builder()
                        .code("0")
                        .message("대기가 완료 되었습니다.")
                        .data(waitingService.postDoneBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ResDTO<ResWaitingPostCancelDTOApiV3>> postCancelBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostCancelDTOApiV3>builder()
                        .code("0")
                        .message("대기가 취소 되었습니다.")
                        .data(waitingService.postCancelBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@PathVariable UUID id,@RequestHeader("X-User-Id")Long userId){
        waitingService.deleteBy(id,userId);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0")
                        .message("대기열 정보가 삭제 되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/internal/{id}/call")
    public ResponseEntity<ResDTO<Object>> getCallById(@PathVariable UUID id){
        slackKafkaServiceApiV1.getCallById(id);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0")
                        .message("대기 호출이 완료되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}

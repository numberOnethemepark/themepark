package com.business.themeparkservice.waiting.presentation.controller.v2;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.waiting.application.dto.request.v2.ReqWaitingPostDTOApiV2;
import com.business.themeparkservice.waiting.application.dto.response.v2.*;
import com.business.themeparkservice.waiting.application.service.v2.WaitingServiceApiV2;
import com.business.themeparkservice.waiting.infastructure.feign.service.SlackFeignClientServiceApiV1;
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
@RequestMapping("/v2/waitings")
@RequiredArgsConstructor
public class WaitingControllerApiV2 {

    private final WaitingServiceApiV2 waitingService;

    private final SlackFeignClientServiceApiV1 slackClientService;

    @ApiPermission(roles = {ApiPermission.Role.USER})
    @PostMapping
    public ResponseEntity<ResDTO<ResWaitingPostDTOApiV2>> postBy(
            @Valid @RequestBody ReqWaitingPostDTOApiV2 reqDto, @RequestHeader("X-User-Id")Long userId){

        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDTOApiV2>builder()
                        .code("0")
                        .message("대기열 생성을 성공했습니다.")
                        .data(waitingService.postBy(reqDto,userId))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResWaitingGetByIdDTOApiV2>> getBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetByIdDTOApiV2>builder()
                        .code("0")
                        .message("대기열 조회에 성공했습니다.")
                        .data(waitingService.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResWaitingGetDTOApiV2>> getBy(
            @QuerydslPredicate(root = ThemeparkEntity.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable)
    {

        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetDTOApiV2>builder()
                        .code("0")
                        .message("대기정보 검색에 성공했습니다.")
                        .data(waitingService.getBy(predicate,pageable))
                        .build(),
                HttpStatus.OK
        );
    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PostMapping("/{id}/done")
    public ResponseEntity<ResDTO<ResWaitingPostDoneDTOApiV2>> postDoneBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDoneDTOApiV2>builder()
                        .code("0")
                        .message("대기가 완료 되었습니다.")
                        .data(waitingService.postDoneBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ResDTO<ResWaitingPostCancelDTOApiV2>> postCancelBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostCancelDTOApiV2>builder()
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
        slackClientService.getCallById(id);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0")
                        .message("대기 호출이 완료되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}

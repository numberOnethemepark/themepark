package com.business.themeparkservice.waiting.presentation.controller;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.*;
import com.business.themeparkservice.waiting.common.dto.ResDTO;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/v1/waitings")
public class WaitingControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDTO<ResWaitingPostDTOApiV1>> postBy(
            @Valid @RequestBody ReqWaitingPostDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDTOApiV1>builder()
                        .code(0)
                        .message("대기열 생성을 성공했습니다.")
                        .data(ResWaitingPostDTOApiV1.of(reqDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResWaitingGetByIdDTOApiV1>> getBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetByIdDTOApiV1>builder()
                        .code(0)
                        .message("대기열 조회에 성공했습니다.")
                        .data(ResWaitingGetByIdDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResWaitingGetDTOApiV1>> getBy(
            @PathVariable(required = false) String searchValue,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable)
    {
        List<WaitingEntity> tempWaitings = List.of(
                new WaitingEntity(),
                new WaitingEntity()
        );

        Page<WaitingEntity> tempWaitingPage = new PageImpl<>(
                tempWaitings, pageable, tempWaitings.size()
        );

        return new ResponseEntity<>(
                ResDTO.<ResWaitingGetDTOApiV1>builder()
                        .code(0)
                        .message("대기정보 검색에 성공했습니다.")
                        .data(ResWaitingGetDTOApiV1.of(tempWaitingPage))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<ResDTO<ResWaitingPostDoneDTOApiV1>> postDoneBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostDoneDTOApiV1>builder()
                        .code(0)
                        .message("대기가 완료 되었습니다.")
                        .data(ResWaitingPostDoneDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ResDTO<ResWaitingPostCancelDTOApiV1>> postCancelBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResWaitingPostCancelDTOApiV1>builder()
                        .code(0)
                        .message("대기가 취소 되었습니다.")
                        .data(ResWaitingPostCancelDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("대기열 정보가 삭제 되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}

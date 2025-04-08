package com.business.themeparkservice.waiting.presentation.controller;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/waitings")
public class WaitingControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDTO<ResWaitingPostDTOApiV1>> postWaiting(
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

}

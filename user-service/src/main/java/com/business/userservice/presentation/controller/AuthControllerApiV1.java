package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqAuthJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthControllerApiV1 {

    @PostMapping("/join")
    public ResponseEntity<ResDTO<Object>> joinBy(
        @Valid
        @RequestBody ReqAuthPostJoinDTOApiV1 dto
    ) {
        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("회원가입에 성공하였습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

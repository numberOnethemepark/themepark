package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostLoginDTOApiV1;
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

    @PostMapping("/login")
    public ResponseEntity<ResDTO<ResAuthPostLoginDTOApiV1>> loginBy(
        @Valid
        @RequestBody ReqAuthPostLoginDTOApiV1 dto
    ) {
        ResAuthPostLoginDTOApiV1 tempResDto = ResAuthPostLoginDTOApiV1.of("accessJwt", "refreshJwt");
        return new ResponseEntity<>(
            ResDTO.<ResAuthPostLoginDTOApiV1>builder()
                .code(0)
                .message("로그인에 성공하였습니다.")
                .data(tempResDto)
                .build(),
            HttpStatus.OK
        );
    }
}

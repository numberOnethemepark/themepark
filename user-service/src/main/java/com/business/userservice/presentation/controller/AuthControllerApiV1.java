package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqAuthPostGuestLoginDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostGuestLoginDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostJoinDTOApiV1;
import com.business.userservice.application.service.AuthServiceApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthControllerApiV1 {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/join")
    public ResponseEntity<ResDTO<Object>> joinBy(
        @Valid
        @RequestBody ReqAuthPostJoinDTOApiV1 dto
    ) {
        ResAuthPostJoinDTOApiV1 data = authServiceApiV1.joinBy(dto);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("회원가입에 성공하였습니다.")
                .data(data)
                .build(),
            HttpStatus.OK
        );
    }

    @PostMapping("/guest-login")
    public ResponseEntity<ResDTO<ResAuthPostGuestLoginDTOApiV1>> guestLoginBy(
        @Valid
        @RequestBody ReqAuthPostGuestLoginDTOApiV1 dto
    ) {
        ResAuthPostGuestLoginDTOApiV1 tempResDto = ResAuthPostGuestLoginDTOApiV1.of("accessJwt");
        return new ResponseEntity<>(
            ResDTO.<ResAuthPostGuestLoginDTOApiV1>builder()
                .code(0)
                .message("로그인에 성공하였습니다.")
                .data(tempResDto)
                .build(),
            HttpStatus.OK
        );
    }
}

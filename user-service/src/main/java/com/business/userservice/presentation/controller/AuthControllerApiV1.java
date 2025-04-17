package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostJoinDTOApiV1;
import com.business.userservice.application.service.AuthServiceApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/refresh")
    public ResponseEntity<ResDTO<Object>> refreshToken(@RequestParam String accessToken, @RequestParam String refreshToken) {
        String newAccessToken = authServiceApiV1.refreshToken(accessToken, refreshToken);

        if (newAccessToken.equals(HttpStatus.UNAUTHORIZED.toString())) {
            return new ResponseEntity<>(
                ResDTO.builder()
                    .code(0)
                    .message("만료된 Refresh Token 입니다. 재로그인을 요청해주세요.")
                    .data(null)
                    .build(),
                HttpStatus.UNAUTHORIZED
            );
        } else {
            return new ResponseEntity<>(
                ResDTO.builder()
                    .code(0)
                    .message("Access Token 재발급 성공했습니다.")
                    .data(newAccessToken)
                    .build(),
                HttpStatus.OK
            );
        }
    }
}

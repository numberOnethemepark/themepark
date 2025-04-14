package com.business.userservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReqAuthPostLoginDTOApiV1 {

    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @NoArgsConstructor
    @Getter
    public static class User {
        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }
}


package com.business.userservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class ReqAuthPostLoginDTOApiV1 {

    @JsonProperty
    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @Builder
    public static class User {
        @JsonProperty
        @NotBlank(message = "아이디를 입력해주세요.")
        private String username;
        @JsonProperty
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }
}

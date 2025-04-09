package com.business.userservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class ReqAuthPostJoinDTOApiV1 {

    @JsonProperty
    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @Builder
    public static class User {
        @JsonProperty
        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(max = 20, message = "이름은 20자까지 가능합니다.")
        private String username;
        @JsonProperty
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 10, max = 100, message = "비밀번호는 10자 이상 100자 이하까지 가능합니다.")
        private String password;
        @JsonProperty
        @NotBlank(message = "슬랙 아이디를 입력해주세요.")
        private String slackId;
    }
}

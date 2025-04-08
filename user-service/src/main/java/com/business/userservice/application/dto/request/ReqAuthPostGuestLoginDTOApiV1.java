package com.business.userservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class ReqAuthPostGuestLoginDTOApiV1 {

    @JsonProperty
    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @Builder
    public static class User {

        @JsonProperty
        @NotBlank(message = "슬랙 아이디를 입력해주세요.")
        private String slackId;
    }
}

package com.business.userservice.application.dto.request;

import com.business.userservice.domain.user.vo.RoleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqAuthPostJoinDTOApiV1 {

    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @Getter
    @Builder
    public static class User implements JoinRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(max = 20, message = "이름은 20자까지 가능합니다.")
        private String username;
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 10, max = 100, message = "비밀번호는 10자 이상 100자 이하까지 가능합니다.")
        private String password;
        @NotBlank(message = "슬랙 아이디를 입력해주세요.")
        private String slackId;

        @Override
        public RoleType getRole() {
            return RoleType.USER;
        }
    }
}

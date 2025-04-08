package com.business.userservice.application.dto.response;

import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.vo.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Builder
public class ResUserGetDTOApiV1 {

    @JsonProperty
    private UserPage userPage;

    public static ResUserGetDTOApiV1 of(Page<UserEntity> userEntityPage) {
        return ResUserGetDTOApiV1.builder()
            .userPage(new UserPage(userEntityPage))
            .build();
    }

    public static class UserPage extends PagedModel<UserPage.User> {

        public UserPage(Page<UserEntity> userInfoPage) {
            super(
                new PageImpl<>(
                    User.from(userInfoPage.getContent()),
                    userInfoPage.getPageable(),
                    userInfoPage.getTotalElements()
                )
            );
        }

        @Builder
        public static class User {

            @JsonProperty
            private String username;
            @JsonProperty
            private String slackId;
            @JsonProperty
            private RoleType role;
            @JsonProperty
            private Boolean isBlacklisted;

            public static List<User> from(List<UserEntity> userEntityList) {
                return userEntityList.stream()
                    .map(User::from)
                    .toList();
            }

            public static User from(UserEntity userEntity) {
                return User.builder()
                    .username("username")
                    .slackId("slack-id")
                    .role(RoleType.USER)
                    .isBlacklisted(Boolean.FALSE)
                    .build();
            }
        }
    }
}

package com.business.userservice.application.dto.request;

import com.business.userservice.domain.user.vo.RoleType;

public interface JoinRequest {
    String getUsername();
    String getPassword();
    String getSlackId();
    RoleType getRole();
}

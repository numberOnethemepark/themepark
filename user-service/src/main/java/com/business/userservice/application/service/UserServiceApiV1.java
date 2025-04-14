package com.business.userservice.application.service;

import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;

public interface UserServiceApiV1 {
    ResUserGetByIdDTOApiV1 getBy(Long id);
}

package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface UserServiceApiV1 {
    ResUserGetByIdDTOApiV1 getBy(Long id);

    ResUserGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    void putBy(Long id, @Valid ReqUserPutDTOApiV1 dto);
}

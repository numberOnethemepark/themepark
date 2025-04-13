package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostJoinDTOApiV1;

public interface AuthServiceApiV1 {
    ResAuthPostJoinDTOApiV1 joinBy(ReqAuthPostJoinDTOApiV1 dto);
}

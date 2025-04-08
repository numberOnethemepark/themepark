package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserControllerApiV1 {

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
        @PathVariable Long id,
        @Valid @RequestBody ReqUserPutDTOApiV1 dto
    ) {
        return new ResponseEntity<>(
            ResDTO.builder()
                .code(0)
                .message("회원 정보 수정에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }
}

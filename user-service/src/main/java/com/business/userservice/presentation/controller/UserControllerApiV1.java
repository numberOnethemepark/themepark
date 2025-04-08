package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetDTOApiV1;
import com.business.userservice.common.dto.ResDTO;
import com.business.userservice.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResUserGetByIdDTOApiV1>> getById(
        @PathVariable Long id
    ) {
        ResUserGetByIdDTOApiV1 tempResDto = ResUserGetByIdDTOApiV1.of("username", "slack-id");
        return new ResponseEntity<>(
            ResDTO.<ResUserGetByIdDTOApiV1>builder()
                .code(0)
                .message("회원 조회에 성공하였습니다.")
                .data(tempResDto)
                .build(),
            HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResUserGetDTOApiV1>> getBy(
        @RequestParam(required = false) String searchValue,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
    ) {
        List<UserEntity> tempUsers = List.of(
            new UserEntity(),
            new UserEntity()
        );
        Page<UserEntity> tempUserPage = new PageImpl<>(tempUsers, pageable, tempUsers.size());
        ResUserGetDTOApiV1 tempResDto = ResUserGetDTOApiV1.of(tempUserPage);
        return new ResponseEntity<>(
            ResDTO.<ResUserGetDTOApiV1>builder()
                .code(0)
                .message("회원 리스트 조회에 성공했습니다.")
                .data(tempResDto)
                .build(),
            HttpStatus.OK
        );
    }
}

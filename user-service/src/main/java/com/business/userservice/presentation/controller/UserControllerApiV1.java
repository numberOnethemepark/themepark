package com.business.userservice.presentation.controller;

import com.business.userservice.application.dto.request.ReqUserPostDeleteDTOApiV1;
import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPutDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetDTOApiV1;
import com.business.userservice.application.service.UserServiceApiV1;
import com.business.userservice.domain.user.entity.UserEntity;
import com.github.themepark.common.application.aop.annotation.ApiPermission;
import com.github.themepark.common.application.aop.annotation.ApiPermission.Role;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserControllerApiV1 {

    private final UserServiceApiV1 userService;

    @ApiPermission(roles = {Role.USER})
    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<ResAuthPutDTOApiV1>> putBy(
        @PathVariable Long id,
        @Valid @RequestBody ReqUserPutDTOApiV1 dto
    ) {
        ResAuthPutDTOApiV1 data = userService.putBy(id, dto);

        return new ResponseEntity<>(
            ResDTO.<ResAuthPutDTOApiV1>builder()
                .code("0")
                .message("회원 정보 수정에 성공했습니다.")
                .data(data)
                .build(),
            HttpStatus.OK
        );
    }

    @ApiPermission(roles = {Role.USER, Role.MANAGER, Role.MASTER})
    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResUserGetByIdDTOApiV1>> getBy(
        @PathVariable Long id
    ) {
        ResUserGetByIdDTOApiV1 data = userService.getBy(id);
        return new ResponseEntity<>(
            ResDTO.<ResUserGetByIdDTOApiV1>builder()
                .code("0")
                .message("회원 조회에 성공하였습니다.")
                .data(data)
                .build(),
            HttpStatus.OK
        );
    }

    @ApiPermission(roles = {Role.MANAGER, Role.MASTER})
    @GetMapping
    public ResponseEntity<ResDTO<ResUserGetDTOApiV1>> getBy(
        @QuerydslPredicate(root = UserEntity.class) Predicate predicate,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
    ) {
        ResUserGetDTOApiV1 data = userService.getBy(predicate, pageable);

        return new ResponseEntity<>(
            ResDTO.<ResUserGetDTOApiV1>builder()
                .code("0")
                .message("회원 리스트 조회에 성공했습니다.")
                .data(data)
                .build(),
            HttpStatus.OK
        );
    }

    @ApiPermission(roles = {Role.USER})
    @PostMapping("/{id}/delete")
    public ResponseEntity<ResDTO<Object>> deleteById(
        @PathVariable Long id,
        @RequestBody ReqUserPostDeleteDTOApiV1 dto
    ) {
        userService.deleteById(id, dto);

        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message("회원 탈퇴에 성공했습니다.")
                .build(),
            HttpStatus.OK
        );
    }

    @ApiPermission(roles = {Role.MANAGER, Role.MASTER})
    @PatchMapping("/{id}/is-blacklisted")
    public ResponseEntity<ResDTO<Object>> blacklistById(
        @PathVariable Long id
    ) {
        boolean isBlacklisted = userService.blacklistById(id);
        String message = isBlacklisted ? "블랙리스트 되었습니다." : "블랙리스트 해제 되었습니다.";

        return new ResponseEntity<>(
            ResDTO.builder()
                .code("0")
                .message(message)
                .build(),
            HttpStatus.OK
        );
    }
}

package com.business.themeparkservice.themepark.presentation.controller;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.service.ThemeparkServiceApiV1;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.github.themepark.common.application.aop.annotation.ApiPermission;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/themeparks")
@RequiredArgsConstructor
public class ThemeparkControllerApiV1 {

    private final ThemeparkServiceApiV1 themeparkService;

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PostMapping
    public ResponseEntity<ResDTO<ResThemeparkPostDTOApiv1>> postBy(
            @Valid @RequestBody ReqThemeparkPostDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkPostDTOApiv1>builder()
                        .code(0)
                        .message("테마파크 생성을 성공했습니다.")
                        .data(themeparkService.postBy(reqDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResThemeparkGetByIdDTOApiV1>> getBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResThemeparkGetByIdDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 조회에 성공했습니다.")
                        .data(themeparkService.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResThemeparkGetDTOApiV1>> getBy(
            @QuerydslPredicate(root = ThemeparkEntity.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
    ){

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkGetDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 검색에 성공했습니다.")
                        .data(themeparkService.getBy(predicate,pageable))
                        .build(),
                HttpStatus.OK
        );

    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<ResThemeparkPutDTOApiV1>> putBy(
            @PathVariable UUID id,
            @RequestBody ReqThemeparkPutDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkPutDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 수정을 성공했습니다.")
                        .data(themeparkService.putBy(id,reqDto))
                        .build(),
                HttpStatus.OK
        );
    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@PathVariable UUID id,@RequestHeader("X-User-Id")Long userId){
        themeparkService.deleteBy(id,userId);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("테마파크 삭제를 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}

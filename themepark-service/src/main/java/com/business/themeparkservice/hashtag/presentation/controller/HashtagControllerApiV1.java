package com.business.themeparkservice.hashtag.presentation.controller;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.service.HashtagServiceApiV1;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.github.themepark.common.application.aop.annotation.ApiPermission;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/hashtags")
@RequiredArgsConstructor
public class HashtagControllerApiV1 {

    private final HashtagServiceApiV1 hashtagService;

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PostMapping
    public ResponseEntity<ResDTO<ResHashtagPostDTOApiV1>> postBy(@Valid @RequestBody ReqHashtagPostDTOApiV1 reqDto){
        return new ResponseEntity<>(
                ResDTO.<ResHashtagPostDTOApiV1>builder()
                        .code("0")
                        .message("해시태그 생성을 성공했습니다.")
                        .data(hashtagService.postBy(reqDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResHashtagGetByIdDTOApiV1>> getBy(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResHashtagGetByIdDTOApiV1>builder()
                        .code("0")
                        .message("해시태그 조회에 성공했습니다.")
                        .data(hashtagService.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResHashtagGetDTOApiV1>> getBy(
            @QuerydslPredicate(root = HashtagEntity.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
    ){

        return new ResponseEntity<>(
                ResDTO.<ResHashtagGetDTOApiV1>builder()
                        .code("0")
                        .message("해시태그 검색에 성공했습니다.")
                        .data(hashtagService.getBy(predicate,pageable))
                        .build(),
                HttpStatus.OK
        );

    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<ResHashtagPutDTOApiV1>> putBy(
            @PathVariable UUID id, @RequestBody ReqHashtagPutDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResHashtagPutDTOApiV1>builder()
                        .code("0")
                        .message("해시태그 수정을 성공했습니다.")
                        .data(hashtagService.putBy(id,reqDto))
                        .build(),
                HttpStatus.OK
        );
    }

    @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@PathVariable UUID id,@RequestHeader("X-User-Id")Long userId){
        hashtagService.deleteBy(id,userId);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0")
                        .message("해시태그 삭제를 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}

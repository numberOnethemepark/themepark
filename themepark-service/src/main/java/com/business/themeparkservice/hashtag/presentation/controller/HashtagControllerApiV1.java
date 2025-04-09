package com.business.themeparkservice.hashtag.presentation.controller;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.themepark.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/hashtags")
public class HashtagControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDTO<ResHashtagPostDTOApiV1>> postHashtag(@Valid @RequestBody ReqHashtagPostDTOApiV1 reqDto){
        return new ResponseEntity<>(
                ResDTO.<ResHashtagPostDTOApiV1>builder()
                        .code(0)
                        .message("해시태그 생성을 성공했습니다.")
                        .data(ResHashtagPostDTOApiV1.of(reqDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResHashtagGetByIdDTOApiV1>> getHashtagById(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResHashtagGetByIdDTOApiV1>builder()
                        .code(0)
                        .message("해시태그 조회에 성공했습니다.")
                        .data(ResHashtagGetByIdDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResHashtagGetDTOApiV1>> getHashtag(
            @RequestParam(required = false) String name,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
    ){
        List<HashtagEntity> tempHashtags = List.of(
                new HashtagEntity(),
                new HashtagEntity()
        );

        Page<HashtagEntity> tempHashtagPage = new PageImpl<>(
                tempHashtags, pageable, tempHashtags.size()
        );

        return new ResponseEntity<>(
                ResDTO.<ResHashtagGetDTOApiV1>builder()
                        .code(0)
                        .message("해시태그 검색에 성공했습니다.")
                        .data(ResHashtagGetDTOApiV1.of(tempHashtagPage))
                        .build(),
                HttpStatus.OK
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<ResHashtagPutDTOApiV1>> putHashtag(
            @PathVariable UUID id, @RequestBody ReqHashtagPutDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResHashtagPutDTOApiV1>builder()
                        .code(0)
                        .message("해시태그 수정을 성공했습니다.")
                        .data(ResHashtagPutDTOApiV1.of(id,reqDto))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Void>> deleteHashtag(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<Void>builder()
                        .code(0)
                        .message("해시태그 삭제를 성공했습니다.")
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}

package com.business.themeparkservice.hashtag.presentation.controller;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.themepark.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

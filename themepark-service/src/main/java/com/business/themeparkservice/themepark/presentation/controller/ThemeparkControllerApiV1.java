package com.business.themeparkservice.themepark.presentation.controller;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.common.dto.ResDTO;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
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
@RequestMapping("/v1/themeparks")
public class ThemeparkControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDTO<ResThemeparkPostDTOApiv1>> postThemepark(
            @Valid @RequestBody ReqThemeparkPostDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkPostDTOApiv1>builder()
                        .code(0)
                        .message("테마파크 생성을 성공했습니다.")
                        .data(ResThemeparkPostDTOApiv1.of(reqDto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResThemeparkGetByIdDTOApiV1>> getThemeparkById(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResThemeparkGetByIdDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 조회에 성공했습니다.")
                        .data(ResThemeparkGetByIdDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<ResThemeparkGetDTOApiV1>>getThemepark(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
    ){
        List<ThemeparkEntity> tempThemeparks = List.of(
                new ThemeparkEntity(),
                new ThemeparkEntity()
        );

        Page<ThemeparkEntity> tempThemeparkPage = new PageImpl<>(
                tempThemeparks, pageable, tempThemeparks.size()
        );

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkGetDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 검색에 성공했습니다.")
                        .data(ResThemeparkGetDTOApiV1.of(tempThemeparkPage))
                        .build(),
                HttpStatus.OK
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<ResThemeparkPutDTOApiV1>> putThemepark(
            @PathVariable UUID id,
            @RequestBody ReqThemeparkPutDTOApiV1 reqDto){

        return new ResponseEntity<>(
                ResDTO.<ResThemeparkPutDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 수정을 성공했습니다.")
                        .data(ResThemeparkPutDTOApiV1.of(reqDto,id))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Void>> deleteThemepark(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<Void>builder()
                        .code(0)
                        .message("테마파크 삭제를 성공했습니다.")
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}

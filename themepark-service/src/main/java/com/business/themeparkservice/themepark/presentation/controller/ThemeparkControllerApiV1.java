package com.business.themeparkservice.themepark.presentation.controller;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.common.dto.ResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                HttpStatus.OK
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResThemeparkGetByIdDTOApiV1>> getThemeparkById(@PathVariable UUID id){
        return new ResponseEntity<>(
                ResDTO.<ResThemeparkGetByIdDTOApiV1>builder()
                        .code(0)
                        .message("테마파크 생성을 성공했습니다.")
                        .data(ResThemeparkGetByIdDTOApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }
}

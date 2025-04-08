package com.business.themeparkservice.themepark.presentation.controller;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/themeparks")
public class ThemeparkControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResThemeparkPostDTOApiv1> postThemepark(
            @Valid @RequestBody ReqThemeparkPostDTOApiV1 reqDto){

        return ResponseEntity.ok(ResThemeparkPostDTOApiv1.of(reqDto));
    }
}

package com.business.productservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductExceptionCode implements ExceptionCode {
    PRODUCT_NOT_FOUND("P101", "해당 상품은 없는 정보입니다.", HttpStatus.NOT_FOUND),
    PRODUCT_STOCK_SOLDOUT("P102","해당 상품은 매진되었습니다.", HttpStatus.BAD_REQUEST),;

    private final String code;
    private final String message;
    private final HttpStatus status;

}

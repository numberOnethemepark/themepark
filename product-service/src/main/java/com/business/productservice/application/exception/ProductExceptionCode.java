package com.business.productservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductExceptionCode implements ExceptionCode {
    PRODUCT_NOT_FOUND("P101", "해당 상품은 없는 정보입니다.", HttpStatus.NOT_FOUND),
    PRODUCT_STOCK_SOLDOUT("P102","해당 상품은 매진되었습니다.", HttpStatus.BAD_REQUEST),
    LOCK_FAILED("P103", "요청이 몰려 처리에 실패했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    LOCK_INTERRUPTED("P103", "처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

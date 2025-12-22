package com.example.article_crud.common.exception.service;

import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.exception.dto.CommonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * 처리되지 않은 모든 예외 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorResponse> handleUnexpectedException(Exception e) {
        ApiErrorCode errorCode = ApiErrorCode.COMMON_INTERNAL_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonErrorResponse.from(errorCode));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonErrorResponse> handleBusinessException(BusinessException e) {
        ApiErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(CommonErrorResponse.from(errorCode));
    }

}

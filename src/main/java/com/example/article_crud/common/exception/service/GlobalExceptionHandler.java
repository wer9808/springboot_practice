package com.example.article_crud.common.exception.service;

import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.exception.dto.CommonErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /*
     * 처리되지 않은 모든 예외 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorResponse> handleUnexpectedException(Exception e) {
        ApiErrorCode errorCode = ApiErrorCode.COMMON_INTERNAL_ERROR;
        String message = messageSource.getMessage(errorCode.getMessageKey(), null, LocaleContextHolder.getLocale());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonErrorResponse.from(errorCode, message));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonErrorResponse> handleBusinessException(BusinessException e) {
        ApiErrorCode errorCode = e.getErrorCode();
        String message = messageSource.getMessage(errorCode.getMessageKey(), null, LocaleContextHolder.getLocale());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(CommonErrorResponse.from(errorCode, message));
    }

}

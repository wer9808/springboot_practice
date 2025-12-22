package com.example.article_crud.common.exception.service;

import com.example.article_crud.common.exception.ApiErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final ApiErrorCode errorCode;

    public BusinessException(ApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ApiErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}

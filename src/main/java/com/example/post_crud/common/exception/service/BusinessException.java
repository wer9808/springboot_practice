package com.example.post_crud.common.exception.service;

import com.example.post_crud.common.exception.ApiErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final ApiErrorCode errorCode;

    public BusinessException(ApiErrorCode errorCode) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
    }

    public BusinessException(ApiErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}

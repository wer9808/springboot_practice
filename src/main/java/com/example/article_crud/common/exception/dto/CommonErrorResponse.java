package com.example.article_crud.common.exception.dto;

import com.example.article_crud.common.exception.ApiErrorCode;

public record CommonErrorResponse(
        String code,
        String message
) {
    public static CommonErrorResponse from(ApiErrorCode errorCode) {
        return new CommonErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static CommonErrorResponse from(ApiErrorCode errorCode, String message) {
        return new CommonErrorResponse(errorCode.getCode(), message);
    }
}

package com.example.article_crud.domain.common.dto;

import com.example.article_crud.domain.common.CommonErrorCode;

public record CommonErrorResponse(
        String code,
        String message
) {
    public static CommonErrorResponse from(CommonErrorCode errorCode) {
        return new CommonErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static CommonErrorResponse from(CommonErrorCode errorCode, String message) {
        return new CommonErrorResponse(errorCode.getCode(), message);
    }
}

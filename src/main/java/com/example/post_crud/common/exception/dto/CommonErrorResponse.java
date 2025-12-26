package com.example.post_crud.common.exception.dto;

import com.example.post_crud.common.exception.ApiErrorCode;

public record CommonErrorResponse(
        String code,
        String message
) {
    public static CommonErrorResponse from(ApiErrorCode errorCode, String message) {
        return new CommonErrorResponse(errorCode.getCode(), message);
    }
}

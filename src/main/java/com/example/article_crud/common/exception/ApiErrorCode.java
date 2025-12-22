package com.example.article_crud.common.exception;

import lombok.Getter;

public enum ApiErrorCode {

    // Common Error
    COMMON_INTERNAL_ERROR(500, "COMMON_INTERNAL_ERROR", "Internal Server Error"),
    COMMON_INVALID_REQUEST(400, "COMMON_INVALID_REQUEST", "Invalid Request"),

    // Auth Error
    AUTH_REQUIRED(401, "AUTH_REQUIRED", "Authentication Required"),
    AUTH_TOKEN_EXPIRED(401, "AUTH_TOKEN_EXPIRED", "Token Expired"),
    AUTH_INVALID_TOKEN(401, "AUTH_INVALID_TOKEN", "Invalid Token"),
    AUTH_INVALID_CREDENTIAL(401, "AUTH_INVALID_CREDENTIAL", "Invalid Credential"),
    AUTH_EMAIL_DUPLICATED(409, "AUTH_EMAIL_DUPLICATED", "Email Duplicated"),


    // Permission Error
    PERMISSION_ACCESS_DENIED(403, "PERMISSION_ACCESS_DENIED", "Access Denied"),

    // Article Error
    ARTICLE_NOT_FOUND(404, "ARTICLE_NOT_FOUND", "Article not found"),
    ARTICLE_NO_TITLE(400, "ARTICLE_NO_TITLE", "Title is required"),
    ARTICLE_NO_CONTENT(400, "ARTICLE_NO_CONTENT", "Content is required"),

    // User Error
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User not found")
    ;

    @Getter
    private final int httpStatusCode;

    @Getter
    private final String code;

    @Getter
    private final String message;

    ApiErrorCode(int httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }

}

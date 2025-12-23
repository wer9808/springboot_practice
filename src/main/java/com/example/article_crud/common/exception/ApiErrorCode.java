package com.example.article_crud.common.exception;

import lombok.Getter;

public enum ApiErrorCode {

    // Common Error
    COMMON_INTERNAL_ERROR(500, "COMMON_INTERNAL_ERROR", "error.common.internal_error"),
    COMMON_INVALID_REQUEST(400, "COMMON_INVALID_REQUEST", "error.common.invalid_request"),

    // Auth Error
    AUTH_REQUIRED(401, "AUTH_REQUIRED", "error.auth.required"),
    AUTH_TOKEN_EXPIRED(401, "AUTH_TOKEN_EXPIRED", "error.auth.token_expired"),
    AUTH_INVALID_TOKEN(401, "AUTH_INVALID_TOKEN", "error.auth.invalid_token"),
    AUTH_INVALID_CREDENTIAL(401, "AUTH_INVALID_CREDENTIAL", "error.auth.invalid_credential"),
    AUTH_EMAIL_DUPLICATED(409, "AUTH_EMAIL_DUPLICATED", "error.auth.email_duplicated"),


    // Permission Error
    PERMISSION_ACCESS_DENIED(403, "PERMISSION_ACCESS_DENIED", "error.permission.access_denied"),

    // Article Error
    ARTICLE_NOT_FOUND(404, "ARTICLE_NOT_FOUND", "error.article.not_found"),
    ARTICLE_NO_TITLE(400, "ARTICLE_NO_TITLE", "error.article.no_title"),
    ARTICLE_NO_CONTENT(400, "ARTICLE_NO_CONTENT", "error.article.no_content"),

    // User Error
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "error.user.not_found")
    ;

    @Getter
    private final int httpStatusCode;

    @Getter
    private final String code;

    @Getter
    private final String messageKey;

    ApiErrorCode(int httpStatusCode, String code, String messageKey) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.messageKey = messageKey;
    }

}

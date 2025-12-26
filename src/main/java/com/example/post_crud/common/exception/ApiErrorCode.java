package com.example.post_crud.common.exception;

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

    // Post Error
    POST_NOT_FOUND(404, "POST_NOT_FOUND", "error.post.not_found"),
    POST_NO_TITLE(400, "POST_NO_TITLE", "error.post.no_title"),
    POST_NO_CONTENT(400, "POST_NO_CONTENT", "error.post.no_content"),

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

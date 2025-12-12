package com.example.article_crud.exceptions;

import com.example.article_crud.CommonErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final CommonErrorCode errorCode;

    public BusinessException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(CommonErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}

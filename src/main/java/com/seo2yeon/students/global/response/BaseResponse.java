package com.seo2yeon.students.global.response;

import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    public BaseResponse(SuccessCode successCode, T result) {
        this.isSuccess = true;
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.result = result;
    }

    public BaseResponse(ErrorCode errorCode) {
        this.isSuccess = false;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.result = null;
    }
}

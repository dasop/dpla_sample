package com.kt.mddp.api.comm.vo;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseCode {
    SUCCESS("200", "정상"),
    VALIDATION_FAIL("300", "validation 에러"),
    NOT_AUTHENTICATED("401", "인증되지 않은 사용자"),
    NOT_AUTHORIZED("403", "접근 권한이 없음"),
    COMMON_BUSINESS_ERROR("500", "비즈니스 에러"),
    COMMON_DPLA_ERROR("600", "Dpla 시스템 에러"),
    ERROR("999", "기타 시스템 에러");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @JsonValue
    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isBusinessError() {
        int targetCode = NumberUtils.toInt(this.code);
        return targetCode >= 500 && targetCode < 600;
    }

    public boolean isSuccess() {
        int targetCode = NumberUtils.toInt(this.code);
        return targetCode >= 200 && targetCode < 300;
    }

}
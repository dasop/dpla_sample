package com.kt.dpla.support.lamp.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseType {
    /**
     * 시스템 에러
     */
    SYSTEM_ERROR("S"),
    /**
     * 비즈니스 에러
     */
    BUSINESS_ERROR("E"), BUSINESS_ERROR_DESC("처리실패"),
    /**
     * INFORMATION
     */
    INFORMATION("I"), INFORMATION_SUCCESS("처리 성공");

    private String value;

    ResponseType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }
}
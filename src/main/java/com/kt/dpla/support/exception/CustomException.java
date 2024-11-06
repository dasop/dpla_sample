package com.kt.dpla.support.exception;

import com.kt.mddp.api.comm.vo.ResponseCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ResponseCode errCd;
    private String errMsgCd;
    private String[] errMsg;

    public CustomException(ResponseCode errCd, String errMsgCd, String... errMsg) {
        this.errCd = errCd;
        this.errMsgCd = errMsgCd;
        this.errMsg = errMsg;
    }
}

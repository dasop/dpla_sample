package com.kt.dpla.support.lamp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kt.dpla.support.lamp.type.LogType;
import com.kt.dpla.support.lamp.type.ResponseType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@ToString
@Setter
@Builder
@JsonInclude(NON_NULL)
public class LampLog {
    private String timestamp;
    private String service;
    private String operation;
    private String bizTransactionId;
    private String transactionId;
    private LogType logType;
    private String seq;
    private Object payload;
    private Caller caller;
    private Host host;
    private Response response;
    private User user;
    private Device device;
    private Destination destination;
    private String url;
    private String serviceDomain;
    private Security security;

    public void setInReq(Object payload) {
        this.logType = LogType.IN_REQ;
        this.response = new Response(ResponseType.INFORMATION, "", "", "");
        this.payload = payload;
    }

    public void setInRes(Response response, Object payload) {
        this.logType = LogType.IN_RES;
        this.response = response;
        this.payload = payload;
    }

    public void setOutReq(Destination destination, Object payload) {
        this.logType = LogType.OUT_REQ;
        this.destination = destination;
        this.payload = payload;
    }

    public void setOutRes(Response response, Object payload) {
        this.logType = LogType.OUT_REQ;
        this.payload = payload;
        this.response = response;
    }

    public void setInMsg(Response response, Object payload) {
        this.logType = LogType.IN_MSG;
        this.response = response;
        this.payload = payload;
    }
}

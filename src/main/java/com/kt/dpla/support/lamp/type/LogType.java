package com.kt.dpla.support.lamp.type;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public enum LogType {
    /**
     * 오퍼레이션에 요청이 들어온 경우(전처리)
     */
    IN_REQ,
    /**
     * 요청에 대한 응답처리(후처리)
     */
    IN_RES,
    /**
     * 외부 특정서비스 요청 전처리
     */
    OUT_REQ,
    /**
     * 외부 특정서비스 요청에 대한 응답처리(후처리)
     */
    OUT_RES,
    /**
     * REQ, RES 쌍 없이, 에러 또는 알림 로그를 발생시켜야 하는 경우 별도 트랜젝션 그룹핑 과정없이 1개의 트랜젝션 로그처리
     */
    NOTIFY,
    /**
     * REQ, RES 쌍 없이, 비동기 작업을 하는 경우 별도 트랜젝션 그룹핑 과정없이 1개의 트랜젝션 로그처리
     */
    ASYNC,
    /**
     * 요청에 대한 REQ, RES 의 쌍을 한개의 로그로 처리하는 경우
     */
    IN_MSG,
    /**
     * 응답에 대한 REQ, RES 의 쌍을 한개의 로그로 처리하는 경우
     */
    OUT_MSG
}
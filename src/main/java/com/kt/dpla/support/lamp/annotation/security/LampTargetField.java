package com.kt.dpla.support.lamp.annotation.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 개인정보처리의 대상 권한부여,변경의 대상 ※ 사용자ID와 동일하거나 다를 수 있음
 * ※ SecureEventType별 필드값
 * - LOGIN : 로그인 시도 ID
 * - LOGOUT
 * - CREATE, READ, UPDATE, EXPORT, DELETE : 대상자 ID
 * - PASS : pass 인증키
 * - IPIN : 아이핀 ID
 * - EMAIL : 이메일 주소
 * - PHONE : 핸드폰 번호
 * @return
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LampTargetField {
}

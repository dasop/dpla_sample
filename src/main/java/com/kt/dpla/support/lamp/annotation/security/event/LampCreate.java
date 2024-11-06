package com.kt.dpla.support.lamp.annotation.security.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LampCreate {
    /**
     * 개인정보변경 내역이나, 권한변경 내역, 인증수행목적
     * ※주의:필수암호화대상 값은 필드만 입력하고 값은 제외
     *  - 필수암호화대상: 고유식별자(주민등록번호, 여권번호, 운전면허번호, 외국인등록번호), 비밀번호, 바이오정보, 신용정보(계좌번호, 신용카드번호 등), 위치정보
     * - CREATE : 회원가입
     * @return
     */
    String detail() default "회원가입";
}

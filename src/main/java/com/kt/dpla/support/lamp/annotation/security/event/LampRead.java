package com.kt.dpla.support.lamp.annotation.security.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LampRead {
    /**
     * 타인의 개인정보를 조회할 수 있는 기능이 있는 경우를 구분하기 위한 목적
     * ※일반적인 경우 타인의 개인정보를 조회하는 것은 침해 사례에 해당하나 시스템에서 기능적으로 타인의 개인정보 조회 기능을 제공하는 경우(사전 동의절차를 거쳐 가족이 대리로 업무 처리 등)이를 구분하여 알람 대상에서 제외하기 위한 목적으로 활용
     * (ex. 로그상의 user.id 값과 security.target 값이 다르지만 security.readOther 값이 'Y'일 경우 정상적인 상황으로 판단)
     * - READ : Y/N
     * @return
     */
    String readOther() default "Y";

    /**
     * 다건 데이터 조회 여부
     * @return
     */
    boolean isMultiTarget();
}

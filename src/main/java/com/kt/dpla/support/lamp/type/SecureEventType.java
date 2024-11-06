package com.kt.dpla.support.lamp.type;

public enum SecureEventType {
    LOGIN,
    LOGOUT,
    READ,
    UPDATE,
    EXPORT,
    CREATE,
    DELETE,
    /**
     * pass 인증
     */
    PASS,
    /**
     * ipin 인증
     */
    IPIN,
    /**
     * email 인증
     */
    EMAIL,
    /**
     * 휴대몬 인증
     */
    PHONE

}

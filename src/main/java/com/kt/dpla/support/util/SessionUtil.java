package com.kt.dpla.support.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtil {
	public static Object getAttribute(String name) {
		return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public static void setAttribute(String name, Object obj) {
		RequestContextHolder.getRequestAttributes().setAttribute(name, obj, RequestAttributes.SCOPE_SESSION);
	}

	public static void removeAttribute(String name) {
		RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
}

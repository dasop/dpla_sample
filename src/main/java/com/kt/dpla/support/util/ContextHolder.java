package com.kt.dpla.support.util;

import java.util.HashMap;
import java.util.Map;

public class ContextHolder {
    private static final ThreadLocal<Map<String, Object>> contextHolder = new ThreadLocal<>();

    public static void set(String key, Object value) {
        if (contextHolder.get() == null) {
            contextHolder.set(new HashMap<>());
        }
        contextHolder.get().put(key, value);
    }

    public static void putAll(Map<String, Object> map) {
        contextHolder.set(map);
    }

    public static <T> T get(String key) {
        return (T) (contextHolder.get() != null ? contextHolder.get().get(key) : null);
    }

    public static Map<String, Object> getAll() {
        return contextHolder.get();
    }

    public static void clear() {
        if (contextHolder.get() != null) {
            contextHolder.get().clear();
            contextHolder.remove();
        }
    }
}
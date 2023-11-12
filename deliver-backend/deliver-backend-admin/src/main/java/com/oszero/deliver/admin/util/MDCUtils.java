package com.oszero.deliver.admin.util;

import org.slf4j.MDC;

/**
 * MDC 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MDCUtils {
    public static void set(String key, String value) {
        MDC.put(key, value);
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void clear() {
        MDC.clear();
    }
}

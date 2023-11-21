package com.oszero.deliver.admin.util;

import org.slf4j.MDC;

/**
 * MDC 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MdcUtils {

    /**
     * 设置 MDC
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        MDC.put(key, value);
    }

    /**
     * 获取 MDC 中的数据
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return MDC.get(key);
    }

    /**
     * 清空 MDC
     */
    public static void clear() {
        MDC.clear();
    }
}

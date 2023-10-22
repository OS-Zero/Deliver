package com.oszero.deliver.server.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author zbzbzzz
 */
public class CommonUtil {


    public static String formatToDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static Boolean paramCheck(Map<String, String> map, String key) {
        return !map.containsKey(key) || StrUtil.isBlank(map.get(key));
    }

    public static void paramCheckAndThrow(Map<String, String> map, String key) {
        if (!map.containsKey(key) || StrUtil.isBlank(map.get(key))) {
            throw new LinkProcessException("参数[" + key + "]不存在");
        }
    }

    public static void paramCheckAndDefault(Map<String, String> map, String key, String defaultValue) {
        if (paramCheck(map, key)) {
            map.put(key, defaultValue);
        }
    }

    public static void isJsonArrayAndThrow(Map<String, String> map, String key) {
        if (!isJsonArray(map.get(key))) {
            throw new LinkProcessException("参数[" + key + "]不符合JsonArray定义");
        }
    }

    public static boolean isJsonArray(String str) {
       return JSONUtil.isTypeJSONArray(str);
    }

}

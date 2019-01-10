package com.kk.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hutwanghui on 2019/1/10 14:26.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc: gson工具
 */

public class JsonUtil {
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}

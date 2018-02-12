package com.kk.common.util.http;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by msi- on 2018/2/7.
 */
public class HttpInformationHelper {
    public static String getBodyData(HttpServletRequest request) {
        Map<String, String> parmMap = new HashMap<String, String>();
        //方式一：getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        //参数名称
        Set<String> key = map.keySet();
        //参数迭代器
        Iterator<String> iterator = key.iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            parmMap.put(k, map.get(k)[0]);
        }
        System.out.println("parmMap=====" + parmMap.toString());
        return parmMap.toString();
    }

    public static String getRequestParameter(HttpServletRequest request, HttpServletResponse response) {

        if (null == request) {
            return null;
        }

        String method = request.getMethod();
        String param = null;
        if (method.equalsIgnoreCase("GET")) {
            /**
             获取?后面的字符串
             http://127.0.0.1:8080/test?username=zhangsan&age=100
             -->username=zhangsan&age=100
             http://127.0.0.1:8080/test?{"username":"zhangsan"}
             -->{"username":"zhangsan"}是json字符串
             有了json串就可以映射成对象了
             */
            param = request.getQueryString();
            if (Base64.isBase64(param)) {
                param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
            }
            System.out.println("param:" + param);
        } else {
            param = getBodyData(request);
            if (Base64.isBase64(param)) {
                param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
            }
            System.out.println("param:" + param);
        }
        return param;
    }

}

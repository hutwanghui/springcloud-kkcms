package org.hut.kkcmsmoviejsoup.utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hutwanghui on 2018/11/7.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
public class JsonUtils {

    private static Object GetMovie(int pageSize) throws IOException {
        //设置代理ip 可以使用香港http代理
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "1080");
        Document doc = Jsoup.connect(" https://api.themoviedb.org/3/movie/upcoming?api_key=c2f8fe5b024fded37dbb4202e5657ebd&language=zh-cn&page=" + pageSize)
                .headers(getJsonValueMap())
                .ignoreContentType(true)
                .get();

        JSONObject json = JSONObject.parseObject(doc.body().text());
        return json.get("results");
    }

    public static Map getJsonValueMap() {
        Map header = new HashMap();
        header.put("User-Agent", "Mozilla/8.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        header.put("Accept-Language", "zh-CN,zh;q=0.9");
        header.put("upgrade-insecure-requests", "1");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("Connection", "keep-alive");
        return header;
    }

}

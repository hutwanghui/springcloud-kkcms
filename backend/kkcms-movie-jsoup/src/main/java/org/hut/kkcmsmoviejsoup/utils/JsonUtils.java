package org.hut.kkcmsmoviejsoup.utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

import org.hut.kkcmsmoviejsoup.utils.TMDBMovie;

/**
 * Created by hutwanghui on 2018/11/7.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
public class JsonUtils {

    public static String GetMovie(int pageSize) throws IOException {
        //设置代理ip 可以使用香港http代理
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "1080");
        Document doc = Jsoup.connect(" https://api.themoviedb.org/3/movie/upcoming?api_key=c2f8fe5b024fded37dbb4202e5657ebd&language=zh-cn&page=" + pageSize)
                .headers(getJsonValueMap())
                .ignoreContentType(true)
                .get();

        JSONObject json = JSONObject.parseObject(doc.body().text());

        return json.get("results").toString();
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

    /**
     * "genres": [
     * {
     * "id": 10759,
     * "name": "Action & Adventure"
     * 动作冒险
     * },
     * {
     * "id": 16,
     * "name": "Animation"
     * 动画
     * },
     * {
     * "id": 35,
     * "name": "Comedy"
     * 喜剧
     * },
     * {
     * "id": 80,
     * "name": "Crime"
     * 犯罪
     * },
     * {
     * "id": 99,
     * "name": "Documentary"
     * 纪录片
     * },
     * {
     * "id": 18,
     * "name": "Drama"
     * 戏剧
     * },
     * {
     * "id": 10751,
     * "name": "Family"
     * 家庭
     * },
     * {
     * "id": 10762,
     * "name": "Kids"
     * 儿童
     * },
     * {
     * "id": 9648,
     * "name": "Mystery"
     * 神秘
     * },
     * {
     * "id": 10763,
     * "name": "News"
     * 新闻
     * },
     * {
     * "id": 10764,
     * "name": "Reality"
     * 现实
     * },
     * {
     * "id": 10765,
     * "name": "Sci-Fi & Fantasy"
     * 科幻
     * },
     * {
     * "id": 10766,
     * "name": "Soap"
     * 肥皂
     * },
     * {
     * "id": 10767,
     * "name": "Talk"
     * 谈话
     * },
     * {
     * "id": 10768,
     * "name": "War & Politics"
     * 战争和政治
     * },
     * {
     * "id": 37,
     * "name": "Western"
     * 西方
     * }
     * ]
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HashMap<Integer, HashSet> hashMap = new HashMap();
        for (int i = 1; i < 20; i++) {
            List<TMDBMovie> results = JSONObject.parseArray(GetMovie(i), TMDBMovie.class);
            System.out.println(i + "爬取到电影数据：" + results.size());
            for (TMDBMovie t : results) {
                Integer[] integers = t.getGenre_ids();
                for (Integer ids : integers) {
                    if (hashMap.get(ids) == null) {
                        HashSet hashSet = new HashSet();
                        hashSet.add(t.getTitle());
                        hashMap.put(ids, hashSet);
                    } else {
                        HashSet h = hashMap.get(ids);
                        h.add(t.getTitle());
                        hashMap.put(ids, h);
                    }
                }
            }
        }
        System.out.println(hashMap.toString());
    }
}

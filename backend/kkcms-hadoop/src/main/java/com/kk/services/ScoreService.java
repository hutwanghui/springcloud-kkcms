package com.kk.services;

import com.kk.daos.MovieInfoDao;
import com.kk.daos.ScoreDao;
import com.kk.daos.UserInfoDao;
import com.kk.models.MovieInfo;
import com.kk.models.Score;
import com.kk.models.ScoreJson;
import com.kk.models.UserInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hutwanghui on 2018/7/14 13:38.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Service
public class ScoreService {
    private static String PYWSGIURL = "http://127.0.0.1:8889";

    @Autowired
    private ScoreDao scoreDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MovieInfoDao movieInfoDao;

    public Score save(Score score) {
        return scoreDao.save(score);
    }

    public Score findByUserIdAndMovieId(int userId, String movieId) {
        return scoreDao.findByUserIdAndMovieId(userId, movieId);
    }

    public List<MovieInfo> findAndDoRecommend(Integer userId) {
        List<Score> scores = scoreDao.findByUserId(userId);
        if (scores.size() > 0) {
            JsonObject object = null;
            JsonParser jsonParser = new JsonParser();
            final String[] str = {null};
            UserInfo u = userInfoDao.findByUserId(userId);
            ScoreJson scoreJson = new ScoreJson();
            scoreJson.setUserid(userId.toString());
            scoreJson.setAlgo(u.getSetUpAlgo());
            StringBuffer idAndScore = new StringBuffer();
            for (Score s :
                    scores) {
                idAndScore.append(s.getMovieId() + ":" + s.getScore() + ",");
            }
            idAndScore.deleteCharAt(idAndScore.length() - 1);
            System.out.println("movieid: " + idAndScore.toString());
            scoreJson.setMovieid(idAndScore.toString());
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(2000, TimeUnit.SECONDS)
                    .build();
            //Gson gson = new Gson();
            //使用Gson将对象转换为json字符串
            /*String json = gson.toJson(scoreJson);
			System.out.println("发送的json："+json);*/
            //MediaType  设置Content-Type 标头中包含的媒体类型值
			/*RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
					, json);*/
            FormBody.Builder builder = new FormBody.Builder()
                    .add("userid", scoreJson.getUserid())
                    .add("algo", scoreJson.getAlgo());
            for (Score s :
                    scores) {
                builder.add("movieid", s.getMovieId() + ":" + s.getScore());
            }
            RequestBody requestBodyPost = builder
                    .build();
            Request request = new Request.Builder()
                    .url(PYWSGIURL)//请求的url
                    .post(requestBodyPost)
                    .build();
            //创建/Call
            Call call = okHttpClient.newCall(request);
            //加入队列 异步操作
            call.enqueue(new Callback() {
                //请求错误回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("连接失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //System.out.println("请求response："+response.body().string());
                    str[0] = response.body().string();
                    System.out.println("请求response：" + str[0]);
                }
            });
            if (str.length > 0) {
                List<String> keys = new ArrayList<>();
                List<MovieInfo> reMovies = null;

                object = (JsonObject) jsonParser.parse(str[0]);
                System.out.println("object= " + object);
                Set<Map.Entry<String, JsonElement>> res = object.getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> e :
                        res) {
                    keys.add(e.getKey());
                }
                reMovies = movieInfoDao.findMovieInfoByMovieIdIn(keys);
                return reMovies;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<Score> findScore(Integer userId) {
        List<Score> scores = scoreDao.findByUserId(userId);
        if (scores.size() > 0) {
            return scores;
        } else {
            return null;
        }
    }
}

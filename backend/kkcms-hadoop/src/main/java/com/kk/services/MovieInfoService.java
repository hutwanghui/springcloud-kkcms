package com.kk.services;

import com.kk.daos.MovieInfoDao;
import com.kk.models.Info;
import com.kk.models.MovieInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hutwanghui on 2018/7/14 13:38.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Service
public class MovieInfoService {
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .build();
    private static String baseUrl = "https://api.douban.com/v2/movie/search?";
    private static String infoUrl = "https://api.douban.com/v2/movie/subject/";
    private static Logger logger = LoggerFactory.getLogger(MovieInfoService.class);

    @Autowired
    private MovieInfoDao movieInfoDao;

    @Autowired
    private LoadMovieInfoService loadMovieInfoService;

    public List<MovieInfo> saveAll(List<MovieInfo> movieInfos) {
        return movieInfoDao.save(movieInfos);
    }

    public Page<MovieInfo> findAll(PageRequest pageRequest) {
        return movieInfoDao.findAll(pageRequest);
    }

    public Page<MovieInfo> findSearch(String keyword, PageRequest pageRequest) {
        return movieInfoDao.findMovieInfoByNameLike("%" + keyword + "%", pageRequest);
    }

    public List<MovieInfo> findAll() {
        return movieInfoDao.findAll();
    }


    public MovieInfo findOne(MovieInfo movieInfo) {
        return movieInfoDao.getOne(movieInfo.getMovieId());
    }

    public Info getInfo(String movieName) throws IOException {
        Info info = new Info();
        String attrs = "q=" + movieName;
        String path = baseUrl + attrs;
        Request request = new Request.Builder()
                .url(path)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String resStr = response.body().string();
        JsonParser parse = new JsonParser();
        JsonObject jsonObject = (JsonObject) parse.parse(resStr);
        if (jsonObject != null) {
            JsonObject j1 = jsonObject.getAsJsonObject();
            if (j1 != null) {
                JsonElement j2 = j1.get("subjects");
                if (j2 != null) {
                    JsonElement movieEle = j2.getAsJsonArray().get(0);
                    //设置导演
                    StringBuffer dirs = new StringBuffer();
                    JsonElement directors = movieEle.getAsJsonObject().get("directors");
                    for (Iterator<JsonElement> it = directors.getAsJsonArray().iterator(); it.hasNext(); ) {
                        JsonElement dir = it.next();
                        JsonElement d = dir.getAsJsonObject().get("name");
                        if (d != null) {
                            dirs.append(d.getAsString() + ",");
                        } else {
                            return null;
                        }
                    }
                    dirs.deleteCharAt(dirs.length() - 1);
                    info.setDirecter(dirs.toString());
                    //设置演员
                    StringBuffer actors = new StringBuffer();
                    JsonElement acts = movieEle.getAsJsonObject().get("casts");
                    for (Iterator<JsonElement> it = acts.getAsJsonArray().iterator(); it.hasNext(); ) {
                        JsonElement dir = it.next();
                        JsonElement d = dir.getAsJsonObject().get("name");
                        if (d != null) {
                            actors.append(d.getAsString() + ",");
                        } else {
                            return null;
                        }
                    }
                    actors.deleteCharAt(actors.length() - 1);
                    info.setActor(actors.toString());
                    //设置类型
                    StringBuffer types = new StringBuffer();
                    JsonElement ty = movieEle.getAsJsonObject().get("genres");
                    for (Iterator<JsonElement> it = ty.getAsJsonArray().iterator(); it.hasNext(); ) {
                        JsonElement dir = it.next();
                        types.append(dir.getAsString() + "/");
                    }
                    types.deleteCharAt(types.length() - 1);
                    info.setType(types.toString());
                    //设置图片
                    StringBuffer images = new StringBuffer();
                    JsonElement imgs = movieEle.getAsJsonObject().get("images");
                    images.append(imgs.getAsJsonObject().get("large").getAsString());
                    info.setImages(images.toString());
                    //设置url
                    JsonElement urls = movieEle.getAsJsonObject().get("alt");
                    String url = urls.getAsString();
                    info.setUrl(url);
                    //设置id
                    JsonElement id = movieEle.getAsJsonObject().get("id");
                    String idd = id.getAsString();
                    info.setId(idd);

                    String detailInfoPath = infoUrl + idd;
                    Request detailRequest = new Request.Builder()
                            .url(detailInfoPath)
                            .build();
                    Response deatailResponse = okHttpClient.newCall(detailRequest).execute();
                    String detailResStr = deatailResponse.body().string();
                    JsonObject detailJsonObject = (JsonObject) parse.parse(detailResStr);
                    String summary = detailJsonObject.get("summary").getAsString();
                    info.setMovieSummary(summary);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return info;
    }

    public MovieInfo findById(String movieId) {

        return movieInfoDao.getOne(movieId);
    }

    public MovieInfo uploadMovie(MovieInfo movieInfo) {
        Sort sort = new Sort(Sort.Direction.DESC, "movieId");
        List<MovieInfo> lastMovies = movieInfoDao.findAll(sort);
        Integer lastid = 0;
        for (MovieInfo m : lastMovies
                ) {
            if (lastid < Integer.parseInt(m.getMovieId())) {
                lastid = Integer.parseInt(m.getMovieId());
            }
        }
        lastid += 1;
        //MovieInfo lastMovie = movieInfoDao.findDesc().get(0);
        //Integer lastid = Integer.parseInt(lastMovie.getMovieId())+1;
        logger.debug("当前最大id值为{}", lastid);
        StringBuffer appendStr = new StringBuffer();
        String getPathBase = "https://v2.sg.media-imdb.com/suggests/";
        String title = movieInfo.getName().toLowerCase();
        String t = title.substring(0, 1);
        String lowerPath = title.replaceAll("[^a-z]+", "_");
        String pathTitle = lowerPath.substring(0, lowerPath.length());
        String getPathReal = getPathBase
                + appendStr.append(t).append("/").append(pathTitle).append(".json").toString();
        logger.debug("电影的信息地址为{}", getPathReal);
        String imagePath = null;
        try {
            imagePath = loadMovieInfoService.getImageByJson(getPathReal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("电影的图片地址为{}", imagePath);
        movieInfo.setMovieId(lastid.toString());
        movieInfo.setInfoUrl(getPathReal);
        movieInfo.setImageUrl(imagePath);
        return movieInfoDao.save(movieInfo);
    }

    public boolean exist(String id) {
        return movieInfoDao.existsById(id);
    }
}

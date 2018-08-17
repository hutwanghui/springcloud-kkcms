package com.kk.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.models.MovieInfo;
import com.kk.models.Score;
import com.kk.models.UserRe;
import com.kk.services.KafkaProducer;
import com.kk.services.MovieInfoService;
import com.kk.services.ScoreService;
import com.kk.services.UserReService;
import com.kk.utils.JsonMessage;
import com.kk.MovieCF;
import com.kk.MovieTrain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Created by hutwanghui on 2018/7/14 13:37.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Controller
@RequestMapping("/score")
public class ScoreController {
    private static Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserReService userReService;
    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private KafkaProducer kafkaProducer;

    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/savescore")
    @ResponseBody
    public JsonMessage saveScore(Score score, HttpSession session) {
        JsonMessage jsonMessage = new JsonMessage();
        if (score != null) {
            logger.debug("当前要保存的评分数据为：{}", score);
            System.out.println("当前要保存的评分数据为：" + score);
            Score sd = scoreService.findByUserIdAndMovieId(score.getUserId(), score.getMovieId());
            if (sd != null) {
                logger.debug("该用户对该电影评过分");
                System.out.println("该用户对该电影评过分");
                sd.setScore(score.getScore());
                scoreService.save(sd);
            } else {
                Score s = scoreService.save(score);
            }

            kafkaProducer.send("first", gson.toJson(sd));
            session.setAttribute("isScoreUpdate", true);
            jsonMessage.setStatu("200");
            jsonMessage.setMessage("评分成功");
        } else {
            jsonMessage.setStatu("500");
            jsonMessage.setMessage("不要传入空值");
        }
        return jsonMessage;
    }

    @RequestMapping("/getscore")
    @ResponseBody
    public Score getScore(String userId, String movieId) {
        logger.debug("获取用户id为：{} 电影id为：{}", userId, movieId);
        System.out.println("获取用户id为：" + userId + " 电影id为：" + movieId);
        if (userId != null && userId != "" && movieId != null && movieId != "") {
            int userIdInt = Integer.parseInt(userId);
            return scoreService.findByUserIdAndMovieId(userIdInt, movieId);
        } else {
            return null;
        }
    }

    @RequestMapping("/getuserscore")
    @ResponseBody
    public List<Map<String, Object>> geuUserScores(String userId) {
        logger.debug("正在获取用户id为{}的评分数据", userId);
        System.out.println("正在获取用户id为" + userId + "的评分数据");
        if (userId != null) {
            List<Map<String, Object>> re = new ArrayList<Map<String, Object>>();
            List<Score> scores = scoreService.findScore(Integer.parseInt(userId));
            if (scores != null) {
                for (Score s : scores
                        ) {
                    Map<String, Object> movie = new HashMap<>();
                    movie.put("movieId", s.getMovieId());
                    movie.put("movieName", movieInfoService.findById(s.getMovieId()).getName());
                    movie.put("userId", s.getUserId());
                    movie.put("score", s.getScore());
                    re.add(movie);
                }
            }
            return re;
        } else {
            return null;
        }
    }

    @RequestMapping("/updateUserRe")
    @ResponseBody
    public JsonMessage updateUserRe(HttpSession httpSession, @RequestParam("userid") String userId) {
        JsonMessage jsonMessage = new JsonMessage();
        System.out.println("查查");
        List<UserRe> result = userReService.findAll(Integer.valueOf(userId));
        jsonMessage.setData(result);
        System.out.println(result.size());
        jsonMessage.setStatu("200");
        return jsonMessage;
    }

    @RequestMapping("/deluserre")
    @ResponseBody
    public JsonMessage deleteUserRe(UserRe userRe, HttpSession session) {
        JsonMessage jsonMessage = new JsonMessage();
        boolean isScoreUpdate = (boolean) session.getAttribute("isScoreUpdate");
        //true 表示用户对电影的评分被修改过了，即进行重新写入预测
        if (userRe != null && isScoreUpdate) {
            logger.debug("删除当前用户{}的所有预测", userRe.getUserId());
            System.out.println("删除当前用户的所有预测 " + userRe.getUserId());
            //int i = userReService.deleteByUserId(userRe);
            //if (i > 0) {
            //jsonMessage.setMessage("删除成功，删除预测的用户为: " + userRe.getUserId());
            session.setAttribute("isScoreUpdate", false);
            //}/* else {

        } //else {
        //  jsonMessage.setStatu("500");
        //  jsonMessage.setMessage("删除失败");
        // }
        return jsonMessage;
    }

    @RequestMapping("/savere")
    @ResponseBody
    public JsonMessage scoreRe(UserRe userRe, HttpSession session) {
        JsonMessage jsonMessage = new JsonMessage();
        logger.debug("要保存的userRe={}", userRe);
        System.out.println("要保存的userRe=" + userRe);
        UserRe userRe1 = userReService.findOneByUserAndMovie(userRe);
        if (userRe1 == null) {
            UserRe u = userReService.save(userRe);
            if (u == null) {
                jsonMessage.setStatu("500");
                jsonMessage.setMessage("保存推荐列表失败,保存信息为" + userRe);
            } else {
                session.setAttribute("isScoreUpdate", false);
                jsonMessage.setMessage("保存成功 " + u);
            }
        } else {
            jsonMessage.setStatu("500");
            jsonMessage.setMessage("保存推荐列表失败,该用户预测已存在 " + userRe);
        }
        return jsonMessage;
    }

    @RequestMapping("/getuserre")
    @ResponseBody
    public List<MovieInfo> getScoreRe(String userId) {
        List<MovieInfo> movieInfos = new ArrayList<>();

        if (userId != null && userId != "") {
            logger.debug("获取数据库中的推荐列表的用户为: {}", userId);
            System.out.println("获取数据库中的推荐列表的用户为: " + userId);
            List<UserRe> userRes = userReService.findAll(Integer.parseInt(userId));
            for (UserRe u : userRes) {
                logger.info("=========" + u.getId() + "==========");
                logger.info("=========" + u.getMovieId() + "==========");
                if (movieInfoService.exist(u.getMovieId())) {
                    MovieInfo movieInfo = movieInfoService.findById(u.getMovieId());
                    if (movieInfo != null) {
                        logger.info("=========" + movieInfo.toString() + "==========");
                        movieInfos.add(movieInfo);
                    }
                }

            }
            if (userRes.size() == 0) {
                for (int i = 1; i <= 10; i++) {
                    List<MovieInfo> movieInfo_all = movieInfoService.findAll();
                    Random random = new Random();
                    if (i <= 5) {
                        UserRe userRe = new UserRe();
                        userRe.setMovieId(movieInfo_all.get(random.nextInt(movieInfo_all.size() - 1 + 1) + 1).getMovieId());
                        userRe.setUserId(Integer.valueOf(userId));
                        userReService.save(userRe);
                        movieInfos.add(movieInfoService.findById(userRe.getMovieId()));
                    } else {
                        movieInfos.add(movieInfo_all.get(random.nextInt(movieInfo_all.size() - 1 + 1) + 1));
                    }
                }
            }

        }
        return movieInfos;

    }

    @RequestMapping("/train")
    public void Train(String userId) {

        MovieTrain movieTrain = new MovieTrain();
        System.out.println("进行训练");
        String[] str = {"file:////home/spark/ml-1m", "/home/spark/" + userId + "/person.txt", userId};
        movieTrain.main(str);

    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
            out.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

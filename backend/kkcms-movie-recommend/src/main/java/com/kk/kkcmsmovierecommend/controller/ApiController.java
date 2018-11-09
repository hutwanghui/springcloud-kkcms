package com.kk.kkcmsmovierecommend.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.common.util.http.R;
import com.kk.kkcmsmovierecommend.Service.KafkaProducer;
import com.kk.kkcmsmovierecommend.entity.MovieScorePersonal;
import com.kk.kkcmsmovierecommend.entity.RecommendResult;
import com.kk.kkcmsmovierecommend.entity.TMDBMovie;
import com.kk.kkcmsmovierecommend.entity.UserRelationship;
import com.kk.kkcmsmovierecommend.mapper.MovieScorePersonalMapper;
import com.kk.kkcmsmovierecommend.mapper.RecommendResultMapper;
import com.kk.kkcmsmovierecommend.mapper.TMDBMovieMapper;
import com.kk.kkcmsmovierecommend.mapper.UserRelationshipMapper;
import com.kk.kkcmsmovierecommend.utils.JsonUtils;
import com.netflix.discovery.converters.Auto;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hutwanghui on 2018/11/8.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    private MovieScorePersonalMapper movieScorePersonalMapper;
    @Autowired
    private UserRelationshipMapper userRelationshipMapper;
    @Autowired
    private RecommendResultMapper recommendResultMapper;
    @Autowired
    private KafkaProducer sender;
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("user/getMovies")
    public Set<MovieScorePersonal> getMoviesByUserId(@RequestParam(value = "userId") String userId) {
        UserRelationship relationship = userRelationshipMapper.findByUserId(userId);
        return relationship.getMovieScorePersonals();
    }

    @RequestMapping("user/makefriend")
    public R updateRelation_user(@RequestParam(value = "MyuserId") String MyuserId, @RequestParam(value = "FrienduserId") String FrienduserId) {
        UserRelationship my = userRelationshipMapper.findByUserId(MyuserId);
        UserRelationship newFriend = userRelationshipMapper.findByUserId(FrienduserId);
        my.makeFriendWith(newFriend);
        UserRelationship result = userRelationshipMapper.save(my);
        if (result != null) {
            return R.ok("添加好友成功");
        } else {
            return R.error();
        }
    }

    @RequestMapping("user/getMoviesName")
    public Set<MovieScorePersonal> getMoviesByName(@RequestParam(value = "username") String username) {
        UserRelationship relationship = userRelationshipMapper.findByUsername(username);
        return relationship.getMovieScorePersonals();
    }

    @RequestMapping("movie/favorite")
    public R favoriteMovie(@RequestParam("movieId") String movieId, @RequestParam(value = "username") String username, @RequestParam(value = "favorite") boolean favorite, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        if (userRelationship != null) {
            Set<MovieScorePersonal> movieList = userRelationship.getMovieScorePersonals();
            if (movieList != null && movieList.size() > 0) {
                List<MovieScorePersonal> movieScoreList = movieList.stream()
                        .filter(m -> m.getMovieId().equals(movieId))
                        .collect(Collectors.toList());
                if (movieScoreList.size() > 0) {
                    MovieScorePersonal saveMovieScore = movieScoreList.get(0);
                    saveMovieScore.setFavorite(favorite);
                    movieScorePersonalMapper.save(saveMovieScore);
                    return R.ok("收藏已看过的电影！");
                } else {
                    MovieScorePersonal addMovieFavorite = MovieScorePersonal.builder()
                            .movieId(movieId).favorite(favorite).score(0).userId(userRelationship.getUserId()).build();
                    Set<MovieScorePersonal> addMovies = userRelationship.getMovieScorePersonals();
                    return SaveNotInMovieSet(userRelationship, addMovies, addMovieFavorite);
                }

            } else {
                MovieScorePersonal addMovieFavorite = MovieScorePersonal.builder()
                        .movieId(movieId).favorite(favorite).score(0).userId(userRelationship.getUserId()).build();
                Set<MovieScorePersonal> addMovies = new HashSet<>();
                return SaveNotInMovieSet(userRelationship, addMovies, addMovieFavorite);
            }
        } else {
            return R.error("非法用户！");
        }
    }

    public R SaveNotInMovieSet(UserRelationship userRelationship, Set<MovieScorePersonal> addMovies, MovieScorePersonal addMovieFavorite) {
        if (addMovies.add(addMovieFavorite)) {
            userRelationship.setMovieScorePersonals(addMovies);
            userRelationshipMapper.save(userRelationship);
            return R.ok("收藏未看过的电影！");
        } else {
            return R.error("收藏失败！");
        }
    }

    @RequestMapping("movie/checkIfFavorite")
    public MovieScorePersonal checkIfFavorite(@RequestParam(value = "movieId") String movieId, @RequestParam(value = "username") String username) {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        List<MovieScorePersonal> result = userRelationship.getMovieScorePersonals()
                .stream().filter(movieScorePersonal ->
                        movieScorePersonal.getMovieId().equals(movieId))
                .collect(Collectors.toList());
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }

    }

    @RequestMapping("movie/favorite/get")
    public R getFavoriteMovies(@RequestParam("username") String username) {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        Set<MovieScorePersonal> result = userRelationship
                .getMovieScorePersonals()
                .stream()
                .filter(movieScorePersonal -> movieScorePersonal.isFavorite())
                .map(movieScorePersonal -> {
                    movieScorePersonal.setId(Long.valueOf(movieScorePersonal.getMovieId()));
                    return movieScorePersonal;
                })
                .collect(Collectors.toSet());

        return R.ok()
                .put("page", 1)
                .put("results", result)
                .put("total_pages", 1)
                .put("total_results", result.size() > 0 ? result.size() : 5);
    }

    @RequestMapping("/movie/userRecommend/get")
    public R getUserRecommend(@RequestParam("username") String username) {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        Set<MovieScorePersonal> result = userRelationship.getUserRelationships().stream()
                .flatMap(m -> {
                    System.out.println("************" + m.getUserId());
                    Set<MovieScorePersonal> friendFavoriteAndScore = userRelationshipMapper.findByUserId(m.getUserId()).getMovieScorePersonals();
                    if (friendFavoriteAndScore != null) {
                        return friendFavoriteAndScore.stream().filter(mv -> mv.isFavorite() && mv.getScore() > 3);
                    } else {
                        return null;
                    }
                }).collect(Collectors.toSet());

        return R.ok()
                .put("page", 1)
                .put("results", result)
                .put("total_pages", 1)
                .put("total_results", result.size() > 0 ? result.size() : 5);
    }

    @RequestMapping("movie/modelRecommend/get")
    public R getModelRecommend(@RequestParam("username") String username) {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        List<RecommendResult> recommendResultList = recommendResultMapper.findByUserId(Integer.valueOf(userRelationship.getUserId()));
        int randomInt = RandomUtil.randomInt(1, recommendResultList.size() / 2);
        Set<RecommendResult> result = CollUtil.sub(recommendResultList, randomInt, randomInt + 10 > recommendResultList.size() ? recommendResultList.size() : randomInt + 10)
                .stream()
                .collect(Collectors.toSet());

        return R.ok()
                .put("page", 1)
                .put("results", result)
                .put("total_pages", 1)
                .put("total_results", result.size() > 0 ? result.size() : 5);
    }

    @PostMapping("moviescore/save")
    public R Create_Movie_Score_Personal(@RequestBody MovieScorePersonal movieScorePersonal) throws Exception {
        MovieScorePersonal result = movieScorePersonalMapper.save(movieScorePersonal);

        if (result != null) {
            sender.send(result.getUserId(), result.getScore(), result.getMovieId());
            return R.ok("创建成功！");
        }
        return R.error("已存在，创建失败");
    }

    @PostMapping("userrelationship/save")
    public R Create_User_RelationShip(@RequestBody UserRelationship userRelationship) throws Exception {
        UserRelationship result = userRelationshipMapper.save(userRelationship);
        if (result != null) {
            return R.ok("创建成功！");
        }
        return R.error("已存在，创建失败");
    }


    @RequestMapping("movie/kafka/score")
    public R score(@RequestParam("movieId") String movieId, @RequestParam("score") int score, @RequestParam("username") String username) {
        UserRelationship userRelationship = userRelationshipMapper.findByUsername(username);
        String userId = userRelationship.getUserId();
        MovieScorePersonal movieScorePersonal = MovieScorePersonal.builder()
                .userId(userId).score(score).movieId(movieId).favorite(false).watchlist(false).rated(true).build();
        if (userRelationship.getMovieScorePersonals() != null) {
            userRelationship.makeScoreWithExist(movieScorePersonal);
        } else {
            userRelationship.makeScoreWith(movieScorePersonal);
        }
        userRelationshipMapper.save(userRelationship);
        sender.send("movie", gson.toJson(movieScorePersonal));
        return R.ok();
    }

//    @RequestMapping("/train")
//    public void Train(String userId) {
//        MovieTrain movieTrain = new MovieTrain();
//        System.out.println("进行训练");
//        String[] str = {"file:////home/spark/ml-1m", "/home/spark/" + userId + "/person.txt", userId};
//        MovieTrain.main(str);
//    }

    @Autowired
    private TMDBMovieMapper tmdbMovieMapper;

    @RequestMapping(value = "/movie/spidder")
    public void spider(@RequestParam("pagestart") Integer pageStart) throws IOException {
        for (int i = pageStart; i < 1000; i++) {
            List<TMDBMovie> results = JSONObject.parseArray(JsonUtils.GetMovie(i), TMDBMovie.class);
            System.out.println(i + "爬取到电影数据：" + results.size());
            for (TMDBMovie t : results) {
                System.out.println(t.toString());
                tmdbMovieMapper.save(t);
            }
        }
    }

    @RequestMapping("test")
    public R test() {
        return R.ok();
    }

    @RequestMapping("initratingData")
    public void rating() {
        List<TMDBMovie> movies = tmdbMovieMapper.findAll();
        System.out.println("%%%%%%%%%%%%" + movies.size());
        CsvWriter writer = CsvUtil.getWriter("f://tmp//ratings.csv", CharsetUtil.UTF_8);
        movies.stream().forEach(s -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("正在生成随机评分数据！！" + s.getId());
                StringBuilder str = new StringBuilder();
                str.append(RandomUtil.randomInt(1, 10) + "::")
                        .append(s.getId() + "::")
                        .append((s.getVote_average() > 3 ?
                                s.getVote_average() - RandomUtil.randomInt(1, 2) : s.getVote_average() + RandomUtil.randomInt(1, 2)) + "::")
                        .append(RandomUtil.randomInt(11, 20));
                writer.write(str.toString().split("::"));
            }
        });
        System.out.println("完成！");
    }

    @RequestMapping("initratingMovies")
    public void movies() {
        List<TMDBMovie> movies = tmdbMovieMapper.findAll();
        System.out.println("%%%%%%%%%%%%" + movies.size());
        CsvWriter writer = CsvUtil.getWriter("f://tmp//movies.csv", CharsetUtil.UTF_8);
        movies.stream().forEach(s -> {

            System.out.println("正在生成电影数据！！" + s.getId());
            StringBuilder str = new StringBuilder();
            str.append(s.getId() + "::")
                    .append(s.getTitle() + "::")
                    .append(s.getOriginal_language());
            writer.write(str.toString().split("::"));
        });
        System.out.println("完成！");
    }
}

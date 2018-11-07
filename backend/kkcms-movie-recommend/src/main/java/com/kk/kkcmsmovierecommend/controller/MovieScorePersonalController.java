package com.kk.kkcmsmovierecommend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.MovieTrain;
import com.kk.common.util.http.R;
import com.kk.kkcmsmovierecommend.Service.KafkaProducer;
import com.kk.kkcmsmovierecommend.entity.MovieScorePersonal;
import com.kk.kkcmsmovierecommend.entity.UserRelationship;
import com.kk.kkcmsmovierecommend.mapper.MovieScorePersonalMapper;
import com.kk.kkcmsmovierecommend.mapper.UserRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hutwanghui on 2018/11/2.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
@RequestMapping("/api/v1.0")
public class MovieScorePersonalController {

    @Autowired
    private MovieScorePersonalMapper movieScorePersonalMapper;
    @Autowired
    private UserRelationshipMapper userRelationshipMapper;
    @Autowired
    private KafkaProducer sender;
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/user/getMovies")
    public Set<MovieScorePersonal> getMoviesByUserId(@RequestParam(value = "userId") String userId) {
        UserRelationship relationship = userRelationshipMapper.findByUserId(userId);
        return relationship.getMovieScorePersonals();
    }

    @RequestMapping("/user/makefriend")
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

    @RequestMapping("/user/getMoviesName")
    public Set<MovieScorePersonal> getMoviesByName(@RequestParam(value = "username") String username) {
        UserRelationship relationship = userRelationshipMapper.findByUsername(username);
        return relationship.getMovieScorePersonals();
    }

    @RequestMapping("/movie/favorite")
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

    @RequestMapping("/movie/checkIfFavorite")
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

    @RequestMapping("/movie/favorite/get")
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
        System.out.println("&&&&&&&&&" + username);
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

    @RequestMapping("/movie/modelRecommend/get")
    public R getModelRecommend(@RequestParam("username") String username) {
        return R.ok();
    }

    @PostMapping("/moviescore/save")
    @Transactional
    public R Create_Movie_Score_Personal(@RequestBody MovieScorePersonal movieScorePersonal) throws Exception {
        MovieScorePersonal result = movieScorePersonalMapper.save(movieScorePersonal);

        if (result != null) {
            sender.send(result.getUserId(), result.getScore(), result.getMovieId());
            return R.ok("创建成功！");
        }
        return R.error("已存在，创建失败");
    }

    @PostMapping("/userrelationship/save")
    @Transactional
    public R Create_User_RelationShip(@RequestBody UserRelationship userRelationship) throws Exception {
        UserRelationship result = userRelationshipMapper.save(userRelationship);
        if (result != null) {
            return R.ok("创建成功！");
        }
        return R.error("已存在，创建失败");
    }


    @RequestMapping("/movie/kafka/score")
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

    @RequestMapping("/train")
    public void Train(String userId) {
        MovieTrain movieTrain = new MovieTrain();
        System.out.println("进行训练");
        String[] str = {"file:////home/spark/ml-1m", "/home/spark/" + userId + "/person.txt", userId};
        MovieTrain.main(str);
    }
}

package com.kk.kkcmsmovierecommend.controller;

import com.kk.common.util.http.R;
import com.kk.kkcmsmovierecommend.Service.KafkaProducer;
import com.kk.kkcmsmovierecommend.entity.MovieScorePersonal;
import com.kk.kkcmsmovierecommend.entity.UserRelationship;
import com.kk.kkcmsmovierecommend.mapper.MovieScorePersonalMapper;
import com.kk.kkcmsmovierecommend.mapper.UserRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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


}

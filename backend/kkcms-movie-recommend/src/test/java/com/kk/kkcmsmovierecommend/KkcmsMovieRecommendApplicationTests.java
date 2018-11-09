package com.kk.kkcmsmovierecommend;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.kk.kkcmsmovierecommend.entity.RecommendResult;
import com.kk.kkcmsmovierecommend.entity.UserRelationship;
import com.kk.kkcmsmovierecommend.mapper.RecommendResultMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsMovieRecommendApplicationTests {


    @Autowired
    private RecommendResultMapper recommendResultMapper;

    @Test
    public void contextLoads() {
        System.out.println(recommendResultMapper.existsByUserIdAndMovieId(1, 22584));
    }

    @Test
    public void findByUserId() {
        List<RecommendResult> userRelationship = recommendResultMapper.findByUserId(1);

        int randomNum = RandomUtil.randomInt(1, 5);
        Set<RecommendResult> result = CollUtil.sub(recommendResultMapper.findByUserId(1), randomNum, randomNum + 3).stream()
                .limit(5)
                .collect(Collectors.toSet());
        System.out.println(randomNum + "=============" + result.toString());
    }

    //=============[RecommendResult(id=5, movieId=6522, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=6, movieId=9340, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=7, movieId=1018, userId=1, event=2018-11-09 21:26:28.0)]
    //4=============[RecommendResult(id=2, movieId=377319, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=4, movieId=11442, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=3, movieId=786, userId=1, event=2018-11-09 21:26:28.0)]
    //=============
    // [RecommendResult(id=5, movieId=6522, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=1, movieId=22584, userId=1, event=2018-11-09 21:26:27.0)
    // , RecommendResult(id=2, movieId=377319, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=4, movieId=11442, userId=1, event=2018-11-09 21:26:28.0)
    // , RecommendResult(id=3, movieId=786, userId=1, event=2018-11-09 21:26:28.0)]

}

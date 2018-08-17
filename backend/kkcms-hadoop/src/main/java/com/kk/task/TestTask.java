package com.kk.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.daos.UserInfoDao;
import com.kk.models.Score;
import com.kk.models.UserRe;
import com.kk.services.KafkaConsumer;
import com.kk.services.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

@Component
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    private Gson gson = new GsonBuilder().create();


//    @Scheduled(cron = "0/10 * * * * *")
    public void save() {
        logger.info("更新评分数据");
//        kafkaProducer.send();
        Score score = new Score();
        score.setScore(new Random().nextInt(5));
        score.setMovieId(String.valueOf(new Random().nextInt(1000)));
        score.setUserId(new Random().nextInt(1000));

        kafkaProducer.send("first", gson.toJson(score));
    }


}

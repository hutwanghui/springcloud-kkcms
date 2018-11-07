package com.kk.kkcmsmovierecommend.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.kkcmsmovierecommend.entity.MovieScorePersonal;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * Created by hutwanghui on 2018/11/3.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    public void send(String userId, int score, String movieId) {
        MovieScorePersonal message = MovieScorePersonal.builder().userId(userId).score(score)
                .movieId(movieId).build();
        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send("movie", gson.toJson(message));
    }

    public void send(String topicName, String jsonData) {
        log.info("向kafka推送数据:[{}]", jsonData);
        try {
            kafkaTemplate.send(topicName, jsonData);
        } catch (Exception e) {
            log.error("发送数据出错！！！{}{}", topicName, jsonData);
            log.error("发送数据出错=====>", e);
        }

        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
                log.info("数据发送成功");
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                log.info("数据发送失败");
            }

            @Override
            public boolean isInterestedInSuccess() {
                log.info("数据发送完毕");
                return false;
            }
        });
    }
}

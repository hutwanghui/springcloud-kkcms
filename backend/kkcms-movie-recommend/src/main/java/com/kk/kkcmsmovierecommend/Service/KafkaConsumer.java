package com.kk.kkcmsmovierecommend.Service;

import com.alibaba.fastjson.JSONObject;
import com.kk.kkcmsmovierecommend.entity.RecommendResult;
import com.kk.kkcmsmovierecommend.mapper.RecommendResultMapper;
import lombok.extern.log4j.Log4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by hutwanghui on 2018/11/7.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Component
@Log4j
public class KafkaConsumer {

    @KafkaListener(topics = {"movie"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("kafka的value: " + record.value().toString());

    }

    @Autowired
    private RecommendResultMapper recommendResultMapper;

    @KafkaListener(topics = {"movie_events"})
    public void Click_events(ConsumerRecord<?, ?> record) {
        RecommendResult jsonObject = JSONObject.parseObject(record.value().toString(), RecommendResult.class);
        jsonObject.setEvent(new Date());
        System.out.println(jsonObject);
        if (jsonObject != null && !recommendResultMapper.existsByUserIdAndMovieId(jsonObject.getUserId(), jsonObject.getMovieId())) {
            recommendResultMapper.save(jsonObject);
        }
    }
}

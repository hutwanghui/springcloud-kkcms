package com.kk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kk.models.KafkaMessage;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * Created by hutwanghui on 2018/8/15.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
//生产者
@Component
public class KafkaProducer {


    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    public void send() {
        KafkaMessage message = new KafkaMessage.Builder().setId(new Random(100).nextInt())
                .setMsg("test").setSendTime(new Date()).Builder();
        logger.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send("first", gson.toJson(message));
    }

    public void send(String topicName, String jsonData) {
        logger.info("向kafka推送数据:[{}]", jsonData);
        try {
            kafkaTemplate.send(topicName, jsonData);
        } catch (Exception e) {
            logger.error("发送数据出错！！！{}{}", topicName, jsonData);
            logger.error("发送数据出错=====>", e);
        }

        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
            }

            @Override
            public boolean isInterestedInSuccess() {
                logger.info("数据发送完毕");
                return false;
            }
        });
    }

}

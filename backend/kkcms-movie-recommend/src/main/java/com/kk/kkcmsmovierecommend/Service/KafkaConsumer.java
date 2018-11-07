package com.kk.kkcmsmovierecommend.Service;

import lombok.extern.log4j.Log4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
}

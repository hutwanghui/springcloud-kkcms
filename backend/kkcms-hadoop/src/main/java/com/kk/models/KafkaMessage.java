package com.kk.models;

import java.util.Date;

/**
 * Created by hutwanghui on 2018/8/15.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

public class KafkaMessage {
    private final int id;    //id

    private final String msg; //消息

    private final Date sendTime;  //时间戳

    private KafkaMessage(Builder builder) {
        this.id = builder.id;
        this.msg = builder.msg;
        this.sendTime = builder.sendTime;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    public static class Builder {
        private int id;    //id

        private String msg; //消息

        private Date sendTime;  //时间戳

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setSendTime(Date sendTime) {
            this.sendTime = sendTime;
            return this;
        }

        public KafkaMessage Builder() {
            return new KafkaMessage(this);
        }
    }

}

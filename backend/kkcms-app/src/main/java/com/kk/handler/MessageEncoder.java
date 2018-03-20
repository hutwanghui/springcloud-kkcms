package com.kk.handler;

import com.alibaba.fastjson.JSON;
import com.kk.entity.Message;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

/**
 * Created by msi- on 2018/3/2.
 */
public class MessageEncoder implements javax.websocket.Encoder.Text<Message> {
    @Override
    public String encode(Message object) throws EncodeException {
        return JSON.toJSONString(object);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

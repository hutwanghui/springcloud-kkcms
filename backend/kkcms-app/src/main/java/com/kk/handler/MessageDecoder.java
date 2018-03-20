package com.kk.handler;

import com.alibaba.fastjson.JSON;
import com.kk.entity.Message;

import javax.websocket.DecodeException;
import javax.websocket.EndpointConfig;

/**
 * Created by msi- on 2018/3/2.
 */
public class MessageDecoder implements javax.websocket.Decoder.Text<Message> {
    @Override
    public Message decode(String s) throws DecodeException {
        return JSON.parseObject(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

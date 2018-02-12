package com.kk.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.entity.Message;
import com.kk.mapper.MessageMapper;
import com.kk.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/2/1.
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message> implements IMessageService {
    @Override
    public void testAdd(Message message) {
        this.mapper.testInsert(message);
    }
}

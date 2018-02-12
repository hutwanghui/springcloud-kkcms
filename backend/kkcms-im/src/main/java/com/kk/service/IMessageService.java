package com.kk.service;

import com.kk.common.service.BaseService;
import com.kk.entity.Message;

/**
 * Created by msi- on 2018/2/1.
 */
public interface IMessageService extends BaseService<Message>{
    public void testAdd(Message message);
}

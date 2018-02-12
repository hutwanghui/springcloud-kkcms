package com.kk.mapper;

import com.kk.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;


public interface MessageMapper extends Mapper<Message> {
    public void testInsert(@Param("message") Message message);
}
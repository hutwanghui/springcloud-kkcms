package com.kk.api.mapper;

import com.kk.api.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    public User selectById(@Param("id") int id);
}
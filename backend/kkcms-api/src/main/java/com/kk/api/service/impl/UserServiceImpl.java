package com.kk.api.service.impl;

import com.kk.api.entity.User;
import com.kk.api.mapper.UserMapper;
import com.kk.api.service.IUserService;
import com.kk.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User findPraiseuserUserlist(int id) {
        return mapper.selectById(id);
    }
}

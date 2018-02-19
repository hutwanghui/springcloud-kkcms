package com.kk.api.service;

import com.kk.api.entity.User;
import com.kk.common.service.BaseService;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public interface IUserService extends BaseService<User> {
    public User findPraiseuserUserlist (int id);
}

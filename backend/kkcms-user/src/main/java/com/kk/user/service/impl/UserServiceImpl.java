package com.kk.user.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.KUser;
import com.kk.user.mapper.KUserMapper;
import com.kk.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<KUserMapper, KUser> implements IUserService {


    @Override
    public void register(KUser kUser) {
        mapper.insert(kUser);
    }

    @Override
    public KUser getByUsername(String username) {
        return mapper.getByUsername(username);
    }

    @Override
    public List<KUser> getByKey(KUser key) {
        return mapper.getByKey(key);
    }

    @Override
    public KUser getByMobile(String mobile) {
        return mapper.getByMobilePhone(mobile);
    }

    @Override
    public List<KUser> checkEmail(String email) {
        return mapper.getByEmail(email);
    }


}

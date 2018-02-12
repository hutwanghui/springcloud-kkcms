package com.kk.user.service;

import com.kk.common.service.BaseService;
import com.kk.user.entity.KUser;

import java.util.List;

/**
 * Created by msi- on 2018/1/18.
 */
public interface IUserService extends BaseService<KUser> {
    void register(KUser kUser);
    KUser getByUsername(String username);
    List<KUser> getByKey(KUser key);
    KUser getByMobile(String mobile);
    List<KUser> checkEmail(String email);

}

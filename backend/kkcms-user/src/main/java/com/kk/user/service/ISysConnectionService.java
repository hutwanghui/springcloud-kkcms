package com.kk.user.service;

import com.kk.common.service.BaseService;
import com.kk.user.entity.SysConnection;

/**
 * Created by msi- on 2018/1/27.
 */
public interface ISysConnectionService extends BaseService<SysConnection> {
    public SysConnection getByUserId(String userId);
}

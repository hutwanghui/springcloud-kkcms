package com.kk.user.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysConnection;
import com.kk.user.mapper.SysConnectionMapper;
import com.kk.user.service.ISysConnectionService;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/1/27.
 */
@Service
public class SysConnectionServiceImpl extends BaseServiceImpl<SysConnectionMapper, SysConnection> implements ISysConnectionService {
    @Override
    public SysConnection getByUserId(String userId) {
        return mapper.getByUserId(userId);
    }
}

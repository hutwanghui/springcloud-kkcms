package com.kk.user.service.impl;

import com.kk.common.service.BaseService;
import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysMenu;
import com.kk.user.mapper.SysMenuMapper;
import com.kk.user.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Override
    public List<SysMenu> getAuthorityMenusByUsername(String username) {
        return mapper.getAuthorityMenusByUsername(username);
    }
}

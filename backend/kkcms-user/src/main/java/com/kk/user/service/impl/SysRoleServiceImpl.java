package com.kk.user.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysRole;
import com.kk.user.mapper.SysRoleMapper;
import com.kk.user.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by msi- on 2018/1/18.
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Override
    public String[] getCodeByUsername(String username) {
        List<SysRole> sysRoleList = mapper.getListByUsername(username);
        Set<String> sysRoleSet = new HashSet<String>();
        sysRoleList.forEach(sysRole -> sysRoleSet.add(sysRole.getCode()));
        return sysRoleSet.toArray(new String[sysRoleSet.size()]);
    }
}

package com.kk.user.service;

import com.kk.common.service.BaseService;
import com.kk.user.entity.SysRole;

/**
 * Created by msi- on 2018/1/18.
 */
public interface ISysRoleService extends BaseService<SysRole>{
    public String[] getCodeByUsername(String username);
}

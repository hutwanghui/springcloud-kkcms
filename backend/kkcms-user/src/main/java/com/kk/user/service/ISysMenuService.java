package com.kk.user.service;

import com.kk.common.service.BaseService;
import com.kk.user.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
public interface ISysMenuService extends BaseService<SysMenu> {
    List<SysMenu> getAuthorityMenusByUsername(@Param("username") String username);
}

package com.kk.user.service;

import com.kk.common.service.BaseService;
import com.kk.user.entity.SysElement;
import com.kk.user.po.ElementVo;

import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
public interface ISysElementService extends BaseService<SysElement> {
    List<SysElement> getListByMenuId(Integer menuId);

    List<SysElement> getAuthorityElementsByUsername(String username);

    List<ElementVo> getListByRole(String role);

    String[] getPermissionsByRoles(String[] roles);
}

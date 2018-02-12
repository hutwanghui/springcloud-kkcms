package com.kk.user.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysElement;
import com.kk.user.mapper.SysElementMapper;
import com.kk.user.po.ElementVo;
import com.kk.user.service.ISysElementService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by msi- on 2018/1/19.
 */
@Service
public class SysElementServiceImpl extends BaseServiceImpl<SysElementMapper, SysElement> implements ISysElementService {
    @Override
    public List<SysElement> getListByMenuId(Integer menuId) {
        return mapper.getListByMenuId(menuId);
    }

    @Override
    public List<SysElement> getAuthorityElementsByUsername(String username) {
        return mapper.getAuthorityElementsByUsername(username);
    }

    @Override
    public List<ElementVo> getListByRole(String role) {
        List<ElementVo> resultList = new ArrayList<>();
        List<SysElement> tElements = mapper.getListByRole(role);
        tElements.forEach(tElement -> {
            ElementVo tElementVo = new ElementVo();
            BeanUtils.copyProperties(tElement, tElementVo);
            resultList.add(tElementVo);
        });
        return resultList;
    }

    @Override
    public String[] getPermissionsByRoles(String[] roles) {
        Set<ElementVo> tElementSet = new HashSet<>();
        for (int i = 0; i < roles.length; i++) {
            String role = roles[i];
            List<ElementVo> tElements = getListByRole(role);
            tElementSet.addAll(tElements);
        }

        Set<String> permissions = new HashSet<>();
        for (ElementVo tElement : tElementSet) {
            if (StringUtils.isNotEmpty(tElement.getCode())) {
                String permission = tElement.getCode();
                permissions.add(permission);
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }
}

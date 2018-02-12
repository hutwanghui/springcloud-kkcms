package com.kk.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kk.common.constant.CommonConstant;
import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysAuthority;
import com.kk.user.entity.SysRole;
import com.kk.user.mapper.SysAuthorityMapper;
import com.kk.user.mapper.SysRoleMapper;
import com.kk.user.service.ISysAuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
@Service
public class SysAuthorityServiceImpl extends BaseServiceImpl<SysAuthorityMapper, SysAuthority> implements ISysAuthorityService {

    @Override
    public JSONObject getAuthority(Integer groupId) {
        JSONObject result = new JSONObject();
        JSONArray menuIds = new JSONArray();
        JSONArray elementIds = new JSONArray();
        List<SysAuthority> tAuthorityList = mapper.getListByRoleId(groupId);
        for (SysAuthority tAuthority : tAuthorityList) {
            if (tAuthority.getTypeId().equals(CommonConstant.REEOURCE_MENU_ID)) {
                menuIds.add(tAuthority.getResourceId());
            } else if (tAuthority.getTypeId().equals(CommonConstant.RESOURCE_ELEMENT_ID)) {
                elementIds.add(tAuthority.getResourceId());
            }
        }
        result.put("menuIds", menuIds);
        result.put("elementIds", elementIds);
        return result;
    }
}

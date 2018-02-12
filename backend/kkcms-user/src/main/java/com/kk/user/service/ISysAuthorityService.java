package com.kk.user.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kk.common.service.BaseService;
import com.kk.user.entity.SysAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
public interface ISysAuthorityService extends BaseService<SysAuthority> {
   // boolean saveBatch(Integer groupId, JSONArray menuIds, JSONArray elementIds);

    JSONObject getAuthority(Integer groupId);
}

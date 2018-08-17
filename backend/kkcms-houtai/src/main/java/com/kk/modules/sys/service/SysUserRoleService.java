package com.kk.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.modules.sys.entity.SysUserRoleEntity;

import java.util.List;


/**
 * 用户与角色对应关系
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    void saveOrUpdate(int userId, List<Integer> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Integer> queryRoleIdList(Integer userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Integer[] roleIds);
}

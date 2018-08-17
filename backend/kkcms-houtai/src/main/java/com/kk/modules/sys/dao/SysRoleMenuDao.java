package com.kk.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kk.modules.sys.entity.SysRoleMenuEntity;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
//@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Integer> queryMenuIdList(Integer roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Integer[] roleIds);
}

package com.kk.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kk.modules.sys.entity.SysUserEntity;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 */
//@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Integer userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Integer> queryAllMenuId(Integer userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

}

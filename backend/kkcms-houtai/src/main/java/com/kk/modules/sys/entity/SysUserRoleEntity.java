package com.kk.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 */
@TableName("sys_role_user")
public class SysUserRoleEntity implements Serializable {
	private static final Long serialVersionUID = 1L;
	@TableId
	private Integer id;

	/**
	 * 用户ID
	 */
	private Integer sysUserId;

	/**
	 * 角色ID
	 */
	private Integer sysRoleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Integer getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
}

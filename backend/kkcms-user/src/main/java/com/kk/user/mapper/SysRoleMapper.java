package com.kk.user.mapper;

import com.kk.user.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {
    List<SysRole> getListByUsername(@Param("username") String username);

}
package com.kk.user.mapper;

import com.kk.user.entity.SysAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysAuthorityMapper extends Mapper<SysAuthority> {
    void deleteByRoleId(@Param("roleId") Integer roleId);

    List<SysAuthority> getListByRoleId(@Param("roleId") Integer roleId);
}
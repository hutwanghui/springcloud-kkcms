package com.kk.user.mapper;

import com.kk.user.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {
    List<SysMenu> getAuthorityMenusByUsername(@Param("username") String username);
}
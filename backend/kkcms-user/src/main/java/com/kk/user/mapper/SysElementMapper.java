package com.kk.user.mapper;

import com.kk.user.entity.SysElement;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysElementMapper extends Mapper<SysElement> {
    List<SysElement> getListByMenuId(@Param("menuId") Integer menuId);

    List<SysElement> getAuthorityElementsByUsername(@Param("username") String username);

    List<SysElement> getListByRole(@Param("role") String role);
}
package com.kk.user.mapper;

import com.kk.user.entity.SysConnection;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SysConnectionMapper extends Mapper<SysConnection> {
    public SysConnection getByUserId(@Param("userId") String userId);
}
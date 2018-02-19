package com.kk.api.mapper;

import com.kk.api.entity.Moment;
import com.kk.api.entity.MomentItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MomentMapper extends Mapper<Moment> {
    Moment selectById(@Param("id") int id);
}

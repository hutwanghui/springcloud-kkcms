package com.kk.api.mapper;

import com.kk.api.entity.Comment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    public List<Comment> selectByMomentId(@Param("momentId") int momentId);
}
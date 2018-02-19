package com.kk.api.mapper;

import com.kk.api.entity.Comment;
import com.kk.api.entity.CommentItem;
import com.kk.api.entity.MomentItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    public List<Comment> selectByMomentId(@Param("momentId") int momentId);
    public List<Comment> getMomentItem();
}
package com.kk.api.service.impl;

import com.kk.api.entity.Comment;
import com.kk.api.mapper.CommentMapper;
import com.kk.api.service.ICommentService;
import com.kk.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Override
    public List<Comment> selectByMomentId(int userId) {
        return mapper.selectByMomentId(userId);
    }
}

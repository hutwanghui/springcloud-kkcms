package com.kk.api.service;

import com.kk.api.entity.Comment;
import com.kk.common.service.BaseService;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public interface ICommentService extends BaseService<Comment> {
    public List<Comment> selectByMomentId(int userId);
    public List<Comment> getMomentItem();
}

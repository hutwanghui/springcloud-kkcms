package com.kk.services;

import com.kk.daos.CommentDao;
import com.kk.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by hutwanghui on 2018/7/14 13:36.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public Comment save(Comment comment) {
        return commentDao.save(comment);
    }

    public Page<Comment> findPage(PageRequest pageRequest, String movieId) {
        return commentDao.findCommentByMovieId(movieId, pageRequest);
    }
}

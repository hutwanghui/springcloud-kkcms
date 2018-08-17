package com.kk.daos;

import com.kk.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by hutwanghui on 2018/7/14 13:39.
 * email:zjjhwanhui@163.com
 * qq:472860892
 * desc:
 */

public interface CommentDao extends JpaRepository<Comment, Integer>, PagingAndSortingRepository<Comment, Integer> {
    /**
     * 根据电影id查找评论
     *
     * @param movieId
     * @param pageable
     * @return
     */
    public Page<Comment> findCommentByMovieId(String movieId, Pageable pageable);
}

package com.kk.kkcmsmovierecommend.mapper;

import com.kk.kkcmsmovierecommend.entity.RecommendResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hutwanghui on 2018/11/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Repository
public interface RecommendResultMapper extends JpaRepository<RecommendResult, Long> {

    public boolean existsByUserIdAndMovieId(Integer userId, Integer movieId);

    public List<RecommendResult> findByUserId(Integer userId);
}

package com.kk.kkcmsmovierecommend.mapper;

import com.kk.kkcmsmovierecommend.entity.TMDBMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hutwanghui on 2018/11/7.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Repository
public interface TMDBMovieMapper extends JpaRepository<TMDBMovie, Integer> {
}

package com.kk.kkcmsmovierecommend.mapper;

import com.kk.kkcmsmovierecommend.entity.TMDBMovie1;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hutwanghui on 2018/11/27.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Repository
public interface TMDB1MovieMapper extends GraphRepository<TMDBMovie1> {
}

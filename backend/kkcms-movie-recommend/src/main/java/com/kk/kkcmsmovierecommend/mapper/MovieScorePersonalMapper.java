package com.kk.kkcmsmovierecommend.mapper;

import com.kk.kkcmsmovierecommend.entity.MovieScorePersonal;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hutwanghui on 2018/11/2.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Repository
public interface MovieScorePersonalMapper extends GraphRepository<MovieScorePersonal> {

}

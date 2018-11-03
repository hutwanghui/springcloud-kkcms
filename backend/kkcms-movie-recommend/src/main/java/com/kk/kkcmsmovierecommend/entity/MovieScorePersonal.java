package com.kk.kkcmsmovierecommend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by hutwanghui on 2018/11/2.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NodeEntity
public class MovieScorePersonal extends BaseEntity {
    private int userId;
    private String movieId;
    private int score;
}

package com.kk.kkcmsmovierecommend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hutwanghui on 2018/11/27.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class MovieType extends BaseEntity {
    private int typeId;
    private String name;
    @Relationship(type = "type")
    @JsonProperty("类别")
    private Set<TMDBMovie1> movieScorePersonals;

    public void collectionMovieType(TMDBMovie1 movie1) {
        if (movieScorePersonals == null) {
            movieScorePersonals = new HashSet<>();
            movieScorePersonals.add(movie1);
        } else {
            movieScorePersonals.add(movie1);
        }
    }
}

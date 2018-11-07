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
    private String userId;
    private String movieId;
    private int score;
    private boolean favorite;
    private boolean rated;
    private boolean watchlist;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieScorePersonal)) return false;
        if (!super.equals(o)) return false;

        MovieScorePersonal that = (MovieScorePersonal) o;

        return getMovieId() != null ? getMovieId().equals(that.getMovieId()) : that.getMovieId() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getMovieId() != null ? getMovieId().hashCode() : 0);
        return result;
    }
}

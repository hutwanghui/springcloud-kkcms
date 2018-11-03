package com.kk.kkcmsmovierecommend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
public class UserRelationship extends BaseEntity {
    private String userId;
    private String username;
    private String tag;
    @Relationship(type = "score")
    @JsonProperty("评分")
    private Set<MovieScorePersonal> movieScorePersonals;

    @Relationship(type = "friend")
    @JsonProperty("好友")
    private Set<UserRelationship> userRelationships;

    public void makeFriendWith(UserRelationship friend) {
        if (userRelationships == null) {
            userRelationships = new HashSet<>();
        } else {
            userRelationships.add(friend);
        }
    }

    public void makeScoreWith(MovieScorePersonal movieScorePersonal) {
        if (movieScorePersonals == null) {
            movieScorePersonals = new HashSet<>();
        } else {
            movieScorePersonals.add(movieScorePersonal);
        }
    }

    @Override
    public String toString() {
        return this.userId + " : " + this.username + " 【好友】 => "
                + Optional.ofNullable(this.userRelationships).orElse(
                Collections.emptySet()).stream().map(
                person -> person.getName()).collect(Collectors.toList())
                + " 【评价过的电影】 => "
                + Optional.ofNullable(this.movieScorePersonals).orElse(
                Collections.emptySet()).stream().map(
                person -> person.getName()).collect(Collectors.toList());
    }
}

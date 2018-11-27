package com.kk.kkcmsmovierecommend.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by hutwanghui on 2018/11/27.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@NodeEntity
public class TMDBMovie1 extends BaseEntity {

    private Integer movieId;
    private Float vote_average;
    private String title;
    private Float popularity;
    private Integer[] genre_ids;
}

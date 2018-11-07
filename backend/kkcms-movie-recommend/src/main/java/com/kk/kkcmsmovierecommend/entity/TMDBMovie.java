package com.kk.kkcmsmovierecommend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by hutwanghui on 2018/11/7.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TMDBMovie")
public class TMDBMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer vote_count;
    private String video;
    private Float vote_average;
    private String title;
    private Float popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;

}

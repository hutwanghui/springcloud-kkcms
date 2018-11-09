package com.kk.kkcmsmovierecommend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hutwanghui on 2018/11/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "RecommendResult")
public class RecommendResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer movieId;

    private Integer userId;

    private Date event;
}

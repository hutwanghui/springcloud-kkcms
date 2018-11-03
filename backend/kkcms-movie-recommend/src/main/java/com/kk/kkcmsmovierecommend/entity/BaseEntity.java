package com.kk.kkcmsmovierecommend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;

/**
 * Created by hutwanghui on 2018/11/2.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class BaseEntity {
    @GraphId
    private Long id;

    private String name;
}

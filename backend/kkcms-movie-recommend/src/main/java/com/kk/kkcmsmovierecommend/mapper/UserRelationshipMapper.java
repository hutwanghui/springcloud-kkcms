package com.kk.kkcmsmovierecommend.mapper;

import com.kk.kkcmsmovierecommend.entity.UserRelationship;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by hutwanghui on 2018/11/2.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@Repository
public interface UserRelationshipMapper extends GraphRepository<UserRelationship> {
    /*
	 继承 GraphRepository类，实现增删查改
	 实现自己的接口：通过名字查询可以是单个节点，也可以是一组节点List集合）
	 spring-data-neo4j 支持方法命名约定查询 findBy{属性名}
	 findBy后面的属性名一定要在节点实体类里存在，否则会报错
	 */

    UserRelationship findByUserId(@Param("userId") String userId);

    UserRelationship findByUsername(@Param("username") String username);

    Long deleteByUserId(@Param("userId") String userId);

}

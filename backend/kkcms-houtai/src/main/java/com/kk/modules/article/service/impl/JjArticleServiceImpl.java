package com.kk.modules.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;
import com.kk.modules.article.dao.JjArticleMapper;
import com.kk.modules.article.entity.JjArticle;
import com.kk.modules.article.service.IJjArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Created by msi- on 2018/4/18.
 */
@Service("jjArticleService")
public class JjArticleServiceImpl extends ServiceImpl<JjArticleMapper, JjArticle> implements IJjArticleService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

            String tag = (String) params.get("tag");
        Page<JjArticle> page = this.selectPage(
                new Query<JjArticle>(params).getPage(),
                new EntityWrapper<JjArticle>()
                        .like(StringUtils.isNotBlank(tag), "tags", tag)
                        .orderBy("updatedAt", true)
        );
        return new PageUtils(page);
    }
}

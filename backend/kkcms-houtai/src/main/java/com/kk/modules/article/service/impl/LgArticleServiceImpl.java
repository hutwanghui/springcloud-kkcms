package com.kk.modules.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;
import com.kk.modules.article.dao.LgArticleMapper;
import com.kk.modules.article.entity.LgArticle;
import com.kk.modules.article.service.ILgArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by msi- on 2018/4/18.
 */
@Service("lgArticleService")
public class LgArticleServiceImpl extends ServiceImpl<LgArticleMapper, LgArticle> implements ILgArticleService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String tag = (String) params.get("tag");
        Page<LgArticle> page = this.selectPage(
                new Query<LgArticle>(params).getPage(),
                new EntityWrapper<LgArticle>()
                        .like(StringUtils.isNotBlank(tag), "tags", tag)
                        .orderBy("publish_time", true)
        );
        return new PageUtils(page);
    }
}

package com.kk.modules.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;
import com.kk.modules.article.dao.BlArticleMapper;
import com.kk.modules.article.entity.BlArticle;
import com.kk.modules.article.service.IBlArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by msi- on 2018/4/18.
 */
@Service("blArticleService")
public class BlArticleServiceImpl extends ServiceImpl<BlArticleMapper,BlArticle> implements IBlArticleService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String tag = (String) params.get("tag");
        Page<BlArticle> page = this.selectPage(
                new Query<BlArticle>(params).getPage(),
                new EntityWrapper<BlArticle>()
                        .like(StringUtils.isNotBlank(tag), "tags", tag)
                        .orderBy("create_time", true)
        );
        return new PageUtils(page);
    }
}

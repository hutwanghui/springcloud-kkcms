package com.kk.modules.article.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.modules.article.entity.JjArticle;

import java.util.Map;

/**
 * Created by msi- on 2018/4/18.
 */
public interface IJjArticleService extends IService<JjArticle> {
    public PageUtils queryPage(Map<String, Object> params);
}

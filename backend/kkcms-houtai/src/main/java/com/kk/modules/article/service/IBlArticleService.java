package com.kk.modules.article.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.modules.article.entity.BlArticle;

import java.util.Map;

/**
 * Created by msi- on 2018/4/18.
 */
public interface IBlArticleService extends IService<BlArticle> {
    public PageUtils queryPage(Map<String, Object> params);
}

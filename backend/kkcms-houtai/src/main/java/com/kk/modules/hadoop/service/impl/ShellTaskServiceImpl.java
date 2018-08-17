package com.kk.modules.hadoop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;
import com.kk.modules.article.entity.JjArticle;
import com.kk.modules.hadoop.dao.ShellTaskMapper;
import com.kk.modules.hadoop.entity.ShellTask;
import com.kk.modules.hadoop.service.IShellTaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by msi- on 2018/4/28.
 */
@Service
public class ShellTaskServiceImpl extends ServiceImpl<ShellTaskMapper, ShellTask> implements IShellTaskService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShellTask> page = this.selectPage(
                new Query<ShellTask>(params).getPage(),
                new EntityWrapper<ShellTask>()
                        .orderBy("id", true)
        );
        return new PageUtils(page);
    }
}

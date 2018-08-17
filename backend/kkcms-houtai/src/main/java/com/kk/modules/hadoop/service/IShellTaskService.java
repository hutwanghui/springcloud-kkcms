package com.kk.modules.hadoop.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.modules.hadoop.entity.ShellTask;

import java.util.Map;

/**
 * Created by msi- on 2018/4/28.
 */
public interface IShellTaskService extends IService<ShellTask> {
    public PageUtils queryPage(Map<String, Object> params);
}

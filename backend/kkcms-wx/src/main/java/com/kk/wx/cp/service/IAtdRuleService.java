package com.kk.wx.cp.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.utils.PageUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
public interface IAtdRuleService extends IService<Atd_rule> {
    /**
     * 暴露给后台服务端使用的接口
     *
     * @param params
     * @return
     */
    public PageUtils queryPage(Map<String, Object> params);

    public boolean deleteRule(Integer[] rule_ids);

    public boolean saveRule(Atd_rule atd_rule);

    public boolean updateRule(Atd_rule atd_rule);


    /**
     * 暴露给客户端使用的接口，没有zuul网管拦截和权限认证
     *
     * @param departId
     * @return
     */
    public List<Atd_rule> queryTaskByDepartId(String departId);
}

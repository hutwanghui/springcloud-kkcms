package com.kk.wx.cp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.entity.WxUser;
import com.kk.wx.cp.mapper.Atd_ruleMapper;
import com.kk.wx.cp.service.IAtdRuleService;
import com.kk.wx.cp.utils.PageUtils;
import com.kk.wx.cp.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@Service
public class AtdRuleServiceImpl extends ServiceImpl<Atd_ruleMapper, Atd_rule> implements IAtdRuleService {
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("attendanceName") != null) {
            String attendanceName = (String) params.get("attendanceName");
            Page<Atd_rule> page = this.selectPage(
                    new Query<Atd_rule>(params).getPage(),
                    new EntityWrapper<Atd_rule>()
                            .like(StringUtils.isNotBlank(attendanceName), "attendance_name", attendanceName)
            );
            return new PageUtils(page);
        } else {
            Page<Atd_rule> page = this.selectPage(
                    new Query<Atd_rule>(params).getPage(),
                    new EntityWrapper<Atd_rule>()
            );
            return new PageUtils(page);
        }

    }

    @Override
    public List<Atd_rule> queryTaskByDepartId(String departId) {
        return this.selectList(
                new EntityWrapper<Atd_rule>().like(StringUtils.isNotBlank(departId), "depart_id", departId)
        );
    }

    @Override
    public boolean deleteRule(Integer[] rule_ids) {
        return this.deleteBatchIds(Arrays.asList(rule_ids));
    }

    @Override
    public boolean saveRule(Atd_rule atd_rule) {
        //创建任务的同时给所有指定部门中的人员添加任务初始态

        return this.insert(atd_rule);
    }

    @Override
    public boolean updateRule(Atd_rule atd_rule) {
        return this.updateById(atd_rule);
    }
}

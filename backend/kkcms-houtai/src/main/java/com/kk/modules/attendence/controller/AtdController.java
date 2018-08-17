package com.kk.modules.attendence.controller;

import com.kk.common.annotation.SysLog;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;
import com.kk.modules.attendence.entity.Atd_rule;
import com.kk.modules.attendence.feign.IAtdComplatedFeign;
import com.kk.modules.attendence.feign.IAtdRuleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@RestController
@RequestMapping("/sys/attendance")
public class AtdController {
    @Autowired
    private IAtdComplatedFeign atdComplatedFeign;
    @Autowired
    private IAtdRuleFeign atdRuleFeign;

    /**
     * 获取打卡结果
     *
     * @param params
     * @return
     */
    @GetMapping("/atdComplated/list")
    public R list_complated(@RequestParam Map<String, Object> params) {
        PageUtils page = null;
        page = atdComplatedFeign.queryPage_complated(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取打卡规则
     *
     * @param params
     * @return
     */
    @GetMapping("/atdRule/list")
    public R list_rule(@RequestParam Map<String, Object> params) {
        PageUtils page = null;
        System.out.println("看看params里面有什么" + params.toString());
        page = atdRuleFeign.queryPage_query(params);
        return R.ok().put("page", page);
    }

    /**
     * 删除规则
     */
    @SysLog("删除打卡规则")
    @PostMapping("/atdRule/delete")
    public R delete_rule(@RequestBody Integer[] rule_ids) {
        if (atdRuleFeign.delete_rule(rule_ids))
            return R.ok();
        else
            return R.error();
    }

    @SysLog("增加打卡规则")
    @PostMapping(value = "/atdRule/save")
    public R save_rule(@RequestBody Atd_rule atd_rule) {
        System.out.println("rule的日期" + atd_rule.getAttendanceTimeBegin());
        if (atdRuleFeign.save_rule(atd_rule))
            return R.ok();
        else
            return R.error();
    }

    @SysLog("修改打卡规则")
    @PostMapping("/atdRule/update")
    public R update_rule(@RequestBody Atd_rule atd_rule) {
        if (atdRuleFeign.update_rule(atd_rule))
            return R.ok();
        else
            return R.error();
    }

    @SysLog("更新前信息的获取")
    @GetMapping("/atdRule/info/{id}")
    public R info_rule(@PathVariable(value = "id") Integer id) {
        Atd_rule atd_rule = atdRuleFeign.queryInfo(id);
        return R.ok().put("rule", atd_rule);
    }
}

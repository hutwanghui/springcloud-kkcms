package com.kk.wx.cp.fegin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kk.wx.cp.entity.Atd_complated;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.entity.WxUser;
import com.kk.wx.cp.service.IAtdComplatedService;
import com.kk.wx.cp.service.IAtdRuleService;
import com.kk.wx.cp.service.IWxUserService;
import com.kk.wx.cp.utils.PageUtils;
import com.kk.wx.cp.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@RestController
@RequestMapping("attendFegin")
public class AttendFegin {
    @Autowired
    private IAtdRuleService atdRuleService;
    @Autowired
    private IWxUserService wxUserService;
    @Autowired
    private IAtdComplatedService atdComplatedService;

    @RequestMapping(value = "/atd/rule/query", method = RequestMethod.GET)
    public PageUtils queryPage_query(@RequestParam Map<String, Object> params) {
        return atdRuleService.queryPage(params);
    }

    @RequestMapping(value = "/atd/rule/info/{id}", method = RequestMethod.GET)
    public Atd_rule queryInfo(@PathVariable(value = "id") Integer id) {
        return atdRuleService.selectById(id);
    }

    @RequestMapping(value = "/atd/complated/query", method = RequestMethod.GET)
    public PageUtils queryPage_complated(@RequestParam Map<String, Object> params) {
        return atdComplatedService.queryPage(params);
    }

    @RequestMapping(value = "/atd/rule/delete", method = RequestMethod.POST)
    public boolean delete_rule(@RequestBody Integer[] rule_ids) {
        return atdRuleService.deleteRule(rule_ids);
    }

    @RequestMapping(value = "/atd/rule/save", method = RequestMethod.POST)
    public boolean save_rule(@RequestBody Atd_rule atd_rule) {
        System.out.println("============" + atd_rule.toString());
        boolean result = atdRuleService.saveRule(atd_rule);
        Atd_rule atd_rule_saved=atdRuleService.selectOne(new EntityWrapper<Atd_rule>().like("attendance_name",atd_rule.getAttendanceName()));
        System.out.println("============" + atd_rule_saved.toString());
        if (result) {
            List<WxUser> wxUsers = wxUserService.selectList(new EntityWrapper<WxUser>()
                    .like(StringUtils.isNotBlank(atd_rule.getDepartId()), "depart_ids", atd_rule.getDepartId())
            );
            wxUsers.stream().forEach(wxUser -> {
                Atd_complated atd_complated = new Atd_complated();
                atd_complated.setUserId(wxUser.getUserId());
                atd_complated.setUserName(wxUser.getName());
                atd_complated.setAttendanceType(0);
                atd_complated.setAttendanceRuleId(atd_rule_saved.getId().toString());
                atdComplatedService.insert(atd_complated);
            });
        }
        return result;
    }

    @RequestMapping(value = "/atd/rule/update", method = RequestMethod.POST)
    public boolean update_rule(@RequestBody Atd_rule atd_rule) {
        return atdRuleService.updateRule(atd_rule);
    }
}

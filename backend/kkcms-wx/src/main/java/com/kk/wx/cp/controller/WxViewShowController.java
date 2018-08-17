package com.kk.wx.cp.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kk.wx.cp.entity.Atd_complated;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.entity.WxUser;
import com.kk.wx.cp.interceptor.OAuthRequired;
import com.kk.wx.cp.service.IAtdComplatedService;
import com.kk.wx.cp.service.IAtdRuleService;
import com.kk.wx.cp.service.IWxUserService;
import com.kk.wx.cp.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi- on 2018/5/14.
 */
@RestController
@RequestMapping("/wx/view")
public class WxViewShowController {

    private static Logger logger = LoggerFactory.getLogger(WxViewShowController.class);

    @Autowired
    private IAtdRuleService atdRuleService;
    @Autowired
    private IAtdComplatedService atdComplatedService;
    @Autowired
    private IWxUserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("signin")
    @OAuthRequired
    public ModelAndView signin(HttpServletRequest httpServletRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView("signin");
        String wxUserId = (String) httpServletRequest.getSession().getAttribute("WX_USER_ID");
        System.out.print("weixin_id^^^^^^^^^66" + wxUserId);
        WxUser wxUser = userService.select_one_byWxId(wxUserId);
        String[] departIds = this.splitDepartIds(wxUser.getDepartIds());
        List<List<Atd_rule>> listList = new ArrayList<>();
        for (String str : departIds) {
            List<Atd_rule> rules = atdRuleService.queryTaskByDepartId(str);
            for (Atd_rule rule : rules) {
                rule.setAttendanceAddressJing(userService.getPosition(rule.getAttendanceAddressWei(), rule.getAttendanceAddressJing()).get("address"));
                rule.setAttendanceAddressWei(userService.getPosition(redisUtils.get(wxUserId + "_wei"), redisUtils.get(wxUserId + "_jing")).get("address"));
            }
            listList.add(rules);
        }
        modelAndView.addObject("ruleLists", listList);
        return modelAndView;
    }

    @GetMapping("signin_bendi")
    public ModelAndView signin_bendi(HttpServletRequest httpServletRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView("signin");
        /*String wxUserId = (String) httpServletRequest.getSession().getAttribute("WX_USER_ID");
        WxUser wxUser = userService.select_one_byWxId(wxUserId);
        String[] departIds = this.splitDepartIds(wxUser.getDepartIds());*/
        List<List<Atd_rule>> listList = new ArrayList<>();
        /*for (String str : departIds) {
            List<Atd_rule> rules = atdRuleService.queryTaskByDepartId(str);

            listList.add(rules);
        }*/
        List<Atd_rule> rules = atdRuleService.queryTaskByDepartId("1001");
        redisUtils.set("wanghui_jing", "113.117105");
        redisUtils.set("wanghui_wei", "27.821935");
        for (Atd_rule rule : rules) {
            rule.setAttendanceAddressJing(userService.getPosition(rule.getAttendanceAddressWei(), rule.getAttendanceAddressJing()).get("address"));
            System.out.println("redis中的经纬：" + redisUtils.get("wanghui_jing"));
            rule.setAttendanceAddressWei(userService.getPosition(redisUtils.get("wanghui_wei"), redisUtils.get("wanghui_jing")).get("address"));
        }
        listList.add(rules);
        modelAndView.addObject("ruleLists", listList);
        return modelAndView;
    }

    @GetMapping("complated_bendi")
    public ModelAndView complated_bendi(HttpServletRequest httpServletRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView("complated");
        List<Atd_complated> complated_condition = atdComplatedService.selectList(new EntityWrapper<Atd_complated>().like("user_id", "1"));

        final Integer[] complated_success_time = {0};
        final Integer[] complated_fail_time = {0};
        final Integer[] complated_total_time = {0};
        final Integer[] uncomplated_time = {0};
        complated_condition.stream().forEach(index -> {
            if (index.getAttendanceType() == 2) {
                complated_success_time[0]++;
                complated_total_time[0]++;
            } else if (index.getAttendanceType() == 3 || index.getAttendanceType() == 4 || index.getAttendanceType() == 5) {
                complated_fail_time[0]++;
                complated_total_time[0]++;
            } else {
                uncomplated_time[0]++;
                complated_total_time[0]++;
            }
        });
        modelAndView.addObject("complated_condition", complated_condition);
        modelAndView.addObject("success", complated_success_time[0]);
        modelAndView.addObject("fail", complated_fail_time[0]);
        modelAndView.addObject("total", complated_total_time[0]);
        modelAndView.addObject("uncomplated", uncomplated_time[0]);
        return modelAndView;
    }

    @GetMapping("complated")
    @OAuthRequired
    public ModelAndView complated(HttpServletRequest httpServletRequest) throws IOException {

        ModelAndView modelAndView = new ModelAndView("complated");
        String wxUserId = (String) httpServletRequest.getSession().getAttribute("WX_USER_ID");
        List<Atd_complated> complated_condition = atdComplatedService.selectList(new EntityWrapper<Atd_complated>().like("user_id", wxUserId));
        final Integer[] complated_success_time = {0};
        final Integer[] complated_fail_time = {0};
        final Integer[] complated_total_time = {0};
        final Integer[] uncomplated_time = {0};
        complated_condition.stream().forEach(index -> {
            if (index.getAttendanceType() == 2) {
                complated_success_time[0]++;
                complated_total_time[0]++;
            } else if (index.getAttendanceType() == 3 || index.getAttendanceType() == 4 || index.getAttendanceType() == 5) {
                complated_fail_time[0]++;
                complated_total_time[0]++;
            } else {
                uncomplated_time[0]++;
                complated_total_time[0]++;
            }
        });
        modelAndView.addObject("complated_condition", complated_condition);
        modelAndView.addObject("success", complated_success_time[0]);
        modelAndView.addObject("fail", complated_fail_time[0]);
        modelAndView.addObject("total", complated_total_time[0]);
        modelAndView.addObject("uncomplated", uncomplated_time[0]);
        return modelAndView;
    }

    public String[] splitDepartIds(String departId) {
        String[] strings = StringUtils.split(departId, ";");
        return strings;
    }
}

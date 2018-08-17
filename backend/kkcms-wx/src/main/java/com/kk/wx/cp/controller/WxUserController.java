package com.kk.wx.cp.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kk.wx.cp.entity.Atd_complated;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.interceptor.OAuthRequired;
import com.kk.wx.cp.service.IAtdComplatedService;
import com.kk.wx.cp.service.IAtdRuleService;
import com.kk.wx.cp.utils.R;
import com.kk.wx.cp.utils.RedisUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpMessageSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by msi- on 2018/5/10.
 */
@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    private static Logger logger = LoggerFactory.getLogger(WxUserController.class);

    @Autowired
    private WxCpService WxService;
    @Autowired
    private IAtdRuleService ruleService;
    @Autowired
    private IAtdComplatedService complatedService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/sendMsg")
    public WxCpMessageSendResult sendMsgBySs(@RequestHeader(value = "Token", required = true) String token,
                                             @RequestBody WxCpMessage cpMessage) throws WxErrorException {
        cpMessage.setAgentId(this.WxService.getWxCpConfigStorage().getAgentId());
        return this.WxService.messageSend(cpMessage);
    }

    @PostMapping("/complated")
    @OAuthRequired
    public Map<String, Object> complated(HttpServletRequest httpServletRequest) {
        String wxUserId = (String) httpServletRequest.getSession().getAttribute("WX_USER_ID");
        Map<String, Object> map = new HashMap<>();
        List<Atd_complated> result = complatedService.selectList(new EntityWrapper<Atd_complated>().like("user_id", wxUserId));

        List<Date> result_Date = new ArrayList<>();
        result.stream().forEach(atd_complated -> {
            if (atd_complated.getAttendanceType() == 2) {
                result_Date.add(atd_complated.getAttendanceTime());
            }
        });
        map.put("atd_complated", result_Date);
        return map;
    }

    @PostMapping("/complated_bendi")
    public Map<String, Object> complated_bendi(HttpServletRequest httpServletRequest) {
        String wxUserId = "1";
        Map<String, Object> map = new HashMap<>();
        List<Atd_complated> result = complatedService.selectList(new EntityWrapper<Atd_complated>().like("user_id", wxUserId));

        List<Date> result_Date = new ArrayList<>();
        result.stream().forEach(atd_complated -> {
            if (atd_complated.getAttendanceType() == 2) {
                result_Date.add(atd_complated.getAttendanceTime());
            }
        });
        map.put("atd_complated", result_Date);
        return map;
    }

    @PostMapping("/qiandao")
    @OAuthRequired
    public R qiandao(HttpServletRequest httpServletRequest, @RequestParam("ruleId") Integer ruleId, @RequestParam("address") String address) throws WxErrorException {
        String wxUserId = (String) httpServletRequest.getSession().getAttribute("WX_USER_ID");
        String user_jing = redisUtils.get(wxUserId + "_jing");
        String user_wei = redisUtils.get(wxUserId + "_wei");
        Date nowDate = new Date();
        Atd_rule atd_rule = new Atd_rule();
        atd_rule = ruleService.selectById(ruleId);
        Atd_complated atd_complated = complatedService.getAttendance(ruleId, wxUserId);
        System.out.println("任务" + ruleId + "用户：" + wxUserId + "进行签到" + address);
        if (atd_complated == null) {
            logger.info("此次签到任务异常");
            return R.error("此次签到异常");
        }
        if (atd_complated != null) {
            List<String> week_rule_list = Arrays.asList(atd_rule.getAttendanceWeek().split(","));
            List<Integer> weeklist = week_rule_list.stream().map(week -> this.changeWeek(week)).collect(toList());
            logger.info("当前的星期数:" + nowDate.getDay() + "指定的星期数：" + weeklist.toString());
            if (!weeklist.contains(nowDate.getDate())) {
                logger.info("签到不在指定的日期范围内");
                return R.error("签到不在指定的日期范围内");
            }
        }
        if (atd_complated.getAttendanceType() == 0) {
            if (atd_rule.getAttendanceTimeBegin().getHours() - nowDate.getHours() >= 0 && atd_rule.getAttendanceTimeBegin().getDate() <= nowDate.getDate()) {
                logger.info("未到开始签到时间");
                return R.error("未到开始签到时间");
            } else if (nowDate.getHours() - atd_rule.getAttendanceTimeEnd().getHours() >= 2) {
                logger.info("签到时间超过两个小时，无法完成开始签到");
                atd_complated.setAttendanceType(3);
                atd_complated.setAttendanceTime(nowDate);
                complatedService.qiandao(atd_complated);
                return R.error("签到时间超过两个小时，无法完成开始签到");
            } else {
                if (atd_rule.getAttendanceAddressJing().contains(user_jing) && atd_rule.getAttendanceAddressWei().contains(user_wei)) {
                    logger.info("完成开始签到" + atd_rule.getAttendanceAddressJing().substring(0, 7));
                    atd_complated.setAttendanceType(1);
                    atd_complated.setAttendanceAddress(address);
                    atd_complated.setAttendanceTime(nowDate);
                    complatedService.qiandao(atd_complated);
                } else {
                    logger.info("未在指定地理范围内签到");
                    atd_complated.setAttendanceType(0);
                    atd_complated.setAttendanceAddress(address);
                    atd_complated.setAttendanceTime(nowDate);
                    complatedService.qiandao(atd_complated);
                    return R.error("未在指定地理范围内签到");
                }
            }
        } else if (atd_complated.getAttendanceType() == 1) {
            if (atd_rule.getAttendanceTimeEnd().getHours() - nowDate.getHours() >= 0 && atd_rule.getAttendanceTimeEnd().getDate() >= nowDate.getDate()) {
                logger.info("未到结束签到时间");
                return R.error("未到结束签到时间");
            } else if (nowDate.getHours() - atd_rule.getAttendanceTimeEnd().getHours() >= 2) {
                logger.info("签到时间超过两个小时，无法完成结束签到");
                atd_complated.setAttendanceType(4);
                atd_complated.setAttendanceTime(nowDate);
                complatedService.qiandao(atd_complated);
                return R.error("签到时间超过两个小时，无法完成结束签到");
            } else {
                if (atd_rule.getAttendanceAddressJing().contains(user_jing) && atd_rule.getAttendanceAddressWei().contains(user_wei)) {
                    logger.info("完成结束签到");
                    atd_complated.setAttendanceType(2);
                    atd_complated.setAttendanceAddress(address);
                    atd_complated.setAttendanceTime(nowDate);
                    complatedService.qiandao(atd_complated);
                } else {
                    logger.info("未在指定地理范围内签到");
                    atd_complated.setAttendanceType(1);
                    atd_complated.setAttendanceAddress(address);
                    atd_complated.setAttendanceTime(nowDate);
                    complatedService.qiandao(atd_complated);
                    return R.error("未在指定地理范围内签到");
                }
            }
        } else if (atd_complated.getAttendanceType() == 2) {
            logger.info("您以完成本日的签到任务，请勿重复签到");
            return R.error("您已完成本日的签到任务，请勿重复签到");
        } else {
            logger.info("您的本日签到任务失败，详细失败原因请通过签到完成情况处查询");
            return R.error("您的本日签到任务失败，详细失败原因请通过签到完成情况处查询");
        }
        return R.ok();
    }

    public int changeWeek(String weekString) {
        if (weekString.equals("星期一")) {
            return 1;
        } else if (weekString.equals("星期二")) {
            return 2;
        } else if (weekString.equals("星期三")) {
            return 3;
        } else if (weekString.equals("星期四")) {
            return 4;
        } else if (weekString.equals("星期五")) {
            return 5;
        } else if (weekString.equals("星期六")) {
            return 6;
        } else {
            return 7;
        }
    }

}

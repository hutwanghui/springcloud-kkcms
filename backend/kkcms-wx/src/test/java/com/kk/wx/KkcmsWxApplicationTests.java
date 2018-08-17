package com.kk.wx;

import com.kk.common.util.DateUtil;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.service.IAtdComplatedService;
import com.kk.wx.cp.service.IAtdRuleService;
import com.kk.wx.cp.service.IWxUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsWxApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IAtdComplatedService atdComplatedService;

    @Test
    public void testMapper() {
        Map<String, Object> params = new HashMap<>();
        params.put("attendanceRuleId", "1");
        System.out.println(atdComplatedService.queryPage(params));
    }

    @Autowired
    private IAtdRuleService atdRuleService;

    @Test
    public void testMapper_2() {
        Map<String, Object> params = new HashMap<>();
        params.put("attendanceName", "上下班打卡");
        System.out.println(atdRuleService.queryPage(params));
    }

    @Test
    public void testMapper_without_params() {
        Map<String, Object> params = new HashMap<>();
        System.out.println(atdRuleService.queryPage(params));
    }

    @Test
    public void testMapper_save() {
        Atd_rule atd_rule = new Atd_rule();
        atd_rule.setAttendanceAddressJing("123123");
        atd_rule.setAttendanceAddressWei("456456");
        atd_rule.setAttendanceAdministrators("admin");
        atd_rule.setAttendanceName("上下班打卡规则2");
        atd_rule.setAttendanceWeek("周1周2");
        atd_rule.setAttendanceTimeBegin(new Date());
        atd_rule.setAttendanceTimeEnd(DateUtil.toDate("2018-12-01", "yyyy-MM-dd"));
        atd_rule.setCreateTime(new Date());
        atd_rule.setCreateBy("admin");
        atdRuleService.saveRule(atd_rule);
    }

    @Test
    public void testMapper_delete() {
        Integer[] integers = {2, 3};
        atdRuleService.deleteRule(integers);
    }

    @Test
    public void testMapper_update() {
        Atd_rule atd_rule = new Atd_rule();
        atd_rule = atdRuleService.selectById(1);
        atd_rule.setCreateTime(new Date());
        atdRuleService.updateRule(atd_rule);
    }

    @Autowired
    private IWxUserService wxUserService;

    @Test
    public void testAddress() throws IOException {
        Map<String, String> result = wxUserService.getPosition("27.821935", "113.117105");
        System.out.println("00000000000000" + result.get("address"));
    }

    @Test
    public void testQiandao() {
        String testJ = "113.123";
        String testW = "27.817";
        Atd_rule a = atdRuleService.selectById(5);
        System.out.println("切割后的经度:" + a.getAttendanceAddressJing().substring(0, 7));

    }
}

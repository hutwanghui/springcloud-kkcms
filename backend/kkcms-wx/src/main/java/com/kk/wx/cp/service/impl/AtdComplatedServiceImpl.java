package com.kk.wx.cp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.wx.cp.entity.Atd_complated;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.mapper.Atd_complatedMapper;
import com.kk.wx.cp.service.IAtdComplatedService;
import com.kk.wx.cp.utils.PageUtils;
import com.kk.wx.cp.utils.Query;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpMessageSendResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@Service
public class AtdComplatedServiceImpl extends ServiceImpl<Atd_complatedMapper, Atd_complated> implements IAtdComplatedService {
    @Autowired
    private WxCpService wxCpService;


    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("attendanceRuleId") != null) {
            String attendanceRuleId = (String) params.get("attendanceRuleId");
            Page<Atd_complated> page = this.selectPage(
                    new Query<Atd_complated>(params).getPage(),
                    new EntityWrapper<Atd_complated>()
                            .like(StringUtils.isNotBlank(attendanceRuleId), "attendance_rule_id", attendanceRuleId)
            );
            return new PageUtils(page);
        } else {
            Page<Atd_complated> page = this.selectPage(
                    new Query<Atd_complated>(params).getPage(),
                    new EntityWrapper<Atd_complated>()
            );
            return new PageUtils(page);
        }

    }

    @Override
    public boolean qiandao(Atd_complated atd_complated) throws WxErrorException {
        this.updateById(atd_complated);
        WxCpMessage message = new WxCpMessage();
        message.setToUser(atd_complated.getUserId());
        if (atd_complated.getAttendanceType() == 1) {
            message.setContent("用户" + atd_complated.getUserName() + "完成开始签到，时间：" + atd_complated.getAttendanceTime() + "地点：" + atd_complated.getAttendanceAddress());
        } else if (atd_complated.getAttendanceType() == 2) {
            message.setContent("用户" + atd_complated.getUserName() + "完成结束签到，时间：" + atd_complated.getAttendanceTime() + "地点：" + atd_complated.getAttendanceAddress());
        } else if (atd_complated.getAttendanceType() == 3) {
            message.setContent("用户" + atd_complated.getUserName() + "签到失败：超过开始签到时间两小时以上" + atd_complated.getAttendanceTime());
        } else if (atd_complated.getAttendanceType() == 4) {
            message.setContent("用户" + atd_complated.getUserName() + "签到失败：超过结束签到时间两小时以上" + atd_complated.getAttendanceTime());
        } else if (atd_complated.getAttendanceType() == 5) {
            message.setContent("用户" + atd_complated.getUserName() + "签到失败：地理位置有误" + atd_complated.getAttendanceAddress());
        }
        message.setMsgType(WxConsts.MassMsgType.TEXT);
        message.setAgentId(this.wxCpService.getWxCpConfigStorage().getAgentId());
        message.setToUser("WangHui");
        WxCpMessageSendResult res = wxCpService.messageSend(message);
        return true;
    }

    @Override
    public List<Atd_complated> queryTaskByDepartId(String departId) {
        return this.selectList(new EntityWrapper<Atd_complated>().like(StringUtils.isNotBlank(departId), "depart_id", departId));
    }

    @Override
    public Atd_complated getAttendance(Integer ruleId, String userId) {
        return this.selectOne(new EntityWrapper<Atd_complated>().like(StringUtils.isNotBlank(userId), "user_id", userId)
                .like("attendance_rule_id", String.valueOf(ruleId)).orderBy("attendance_time"));
    }

}

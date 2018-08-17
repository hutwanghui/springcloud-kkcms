package com.kk.wx.cp.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.wx.cp.entity.Atd_complated;
import com.kk.wx.cp.entity.Atd_rule;
import com.kk.wx.cp.utils.PageUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
public interface IAtdComplatedService extends IService<Atd_complated> {

    /**
     * 暴露给后台服务端使用的接口
     *
     * @param params
     * @return
     */
    public PageUtils queryPage(Map<String, Object> params);


    /**
     * 暴露给客户端使用的接口
     *
     * @param
     * @return
     */
    public boolean qiandao(Atd_complated atd_complated) throws WxErrorException;

    public List<Atd_complated> queryTaskByDepartId(String departId);

    public Atd_complated getAttendance(Integer ruleId,String userId);
}

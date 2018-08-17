package com.kk.wx.cp.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.wx.cp.entity.WxDepart;
import com.kk.wx.cp.mapper.WxDepartMapper;
import com.kk.wx.cp.service.ContactWxCpService;
import com.kk.wx.cp.service.IWxDepartService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/5/11.
 */
@Service
public class WxDepartServiceImpl extends ServiceImpl<WxDepartMapper, WxDepart> implements IWxDepartService, WxCpDepartmentService {

    @Autowired
    private ContactWxCpService wxService;

    @Override
    public Integer create(WxCpDepart wxCpDepart) throws WxErrorException {
        return wxService.getDepartmentService().create(wxCpDepart);
    }

    @Override
    public List<WxCpDepart> list(Integer integer) throws WxErrorException {
        return wxService.getDepartmentService().list(integer);
    }

    @Override
    public void update(WxCpDepart wxCpDepart) throws WxErrorException {
        this.wxService.getDepartmentService().update(wxCpDepart);
    }

    @Override
    public void delete(Integer integer) throws WxErrorException {
        this.wxService.getDepartmentService().delete(integer);
    }
}

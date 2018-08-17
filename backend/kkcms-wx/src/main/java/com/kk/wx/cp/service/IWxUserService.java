package com.kk.wx.cp.service;

import com.baomidou.mybatisplus.service.IService;
import com.kk.wx.cp.entity.WxUser;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/11.
 */
public interface IWxUserService extends IService<WxUser> {
    public boolean tbWxUserFromWeb() throws WxErrorException;
    public WxUser select_one_byWxId(String user_Id);
    public Map<String, String> getPosition(String jing, String wei) throws IOException;
}

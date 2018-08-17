package com.kk.wx.cp.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kk.common.util.ObjectUtil;
import com.kk.wx.cp.entity.WxDepart;
import com.kk.wx.cp.entity.WxUser;
import com.kk.wx.cp.mapper.WxDepartMapper;
import com.kk.wx.cp.mapper.WxUserMapper;
import com.kk.wx.cp.service.ContactWxCpService;
import com.kk.wx.cp.service.IWxDepartService;
import com.kk.wx.cp.service.IWxUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/5/11.
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService, WxCpUserService {
    @Autowired
    private ContactWxCpService wxCpService;
    @Autowired
    private RestTemplate restTemplate;
    private static String ak = "gVqRwNndVMzxUnOg0uisGuPPgiaL4Mv7";

    @Override
    public void authenticate(String s) throws WxErrorException {
        this.wxCpService.getUserService().authenticate(s);
    }

    @Override
    public List<WxCpUser> listByDepartment(Integer integer, Boolean aBoolean, Integer integer1) throws WxErrorException {
        return wxCpService.getUserService().listByDepartment(integer, aBoolean, integer1);
    }

    @Override
    public List<WxCpUser> listSimpleByDepartment(Integer integer, Boolean aBoolean, Integer integer1) throws WxErrorException {
        return wxCpService.getUserService().listSimpleByDepartment(integer, aBoolean, integer1);
    }

    @Override
    public void create(WxCpUser wxCpUser) throws WxErrorException {
        this.wxCpService.getUserService().create(wxCpUser);
    }

    @Override
    public void update(WxCpUser wxCpUser) throws WxErrorException {
        this.wxCpService.getUserService().update(wxCpUser);
    }

    @Override
    public void delete(String... strings) throws WxErrorException {
        this.wxCpService.getUserService().delete(strings);
    }

    @Override
    public WxCpUser getById(String s) throws WxErrorException {
        return wxCpService.getUserService().getById(s);
    }

    @Override
    public int invite(String s, String s1) throws WxErrorException {
        return this.wxCpService.getUserService().invite(s, s1);
    }

    @Override
    public Map<String, String> userId2Openid(String s, Integer integer) throws WxErrorException {
        return this.wxCpService.getUserService().userId2Openid(s, integer);
    }

    @Override
    public String openid2UserId(String s) throws WxErrorException {
        return this.wxCpService.getUserService().openid2UserId(s);
    }

    @Override
    public boolean tbWxUserFromWeb() throws WxErrorException {
        //获取所有部门的状态为1的用户
        List<WxCpUser> wxCpUsers = this.listByDepartment(1, true, 1);
        List<WxUser> wxUsers = new ArrayList<>();
        for (WxCpUser wxCpUser : wxCpUsers) {
            WxUser wxUser = ObjectUtil.copyProperties(wxCpUser, WxUser.class);
            wxUsers.add(wxUser);
        }
        return this.insertOrUpdateBatch(wxUsers);
    }

    @Override
    public WxUser select_one_byWxId(String user_Id) {

        return this.selectOne(new EntityWrapper<WxUser>()
                .like(StringUtils.isNotBlank(user_Id), "user_id", user_Id));
    }

    @Override
    public Map<String, String> getPosition(String jing, String wei) throws IOException {
        URL url = new URL("http://api.map.baidu.com/geocoder?ak=" + ak  +
                "&callback=renderReverse&location=" + jing
                + "," + wei + "&output=json");
        URLConnection connection = url.openConnection();

        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
        out.flush();
        out.close();
//        一旦发送成功，用以下方法就可以得到服务器的回应：
        String res;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                l_urlStream, "UTF-8"));
        StringBuilder sb = new StringBuilder("");
        while ((res = in.readLine()) != null) {
            sb.append(res.trim());
        }
        String str = sb.toString();
        System.out.println(str);
        Map<String, String> map = null;
        if (StringUtils.isNotEmpty(str)) {
            int addStart = str.indexOf("formatted_address\":");
            int addEnd = str.indexOf("\",\"business");
            if (addStart > 0 && addEnd > 0) {
                String address = str.substring(addStart + 20, addEnd);
                map = new HashMap<String, String>();
                map.put("address", address);
                return map;
            }
        }

        return null;


    }
}

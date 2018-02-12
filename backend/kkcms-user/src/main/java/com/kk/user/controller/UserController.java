package com.kk.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.kk.common.controller.BaseController;
import com.kk.common.enumtype.ResultCodeEnum;
import com.kk.common.util.CheckUtil;
import com.kk.common.util.JsonUtil;
import com.kk.user.entity.KUser;
import com.kk.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<IUserService, KUser, Integer> {
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JSONObject registerUser(@RequestBody KUser kUser) throws Exception {
        String username = kUser.getUsername();
        String email = kUser.getEmail();
        String mobilePhone = kUser.getMobile();
        if (!CheckUtil.checkEmaile(email)) {
            //邮箱不合法，将不合法的邮箱的code和信息发送至前端
            return JsonUtil.getResultJson(ResultCodeEnum.EMAILCHECKFAIL);
        }
        KUser kUser_check_exist = baseServiceImpl.getByUsername(username);
        if (kUser_check_exist != null) {
            //如果和数据库中比对，存在用户，则将用户存在的code和信息发送至前端
            return JsonUtil.getResultJson(ResultCodeEnum.USER_EXIST);
        }
        KUser check_mobile = baseServiceImpl.getByMobile(mobilePhone);
        if (check_mobile != null) {
            //手机号只能注册一次
            return JsonUtil.getResultJson(ResultCodeEnum.MOBILE_PHONE_CHECK_FAIL);
        }
        List<KUser> check_email_list = baseServiceImpl.checkEmail(email);
        if (check_email_list != null && check_email_list.size() > 0) {
            //邮箱只能注册一次
            return JsonUtil.getResultJson(ResultCodeEnum.EMAIL_EXIST);
        }
        kUser.setCreateTime(new Date());
        kUser.setUpdateTime(new Date());
        baseServiceImpl.register(kUser);
        return JsonUtil.getSuccessJsonObject();
    }


}

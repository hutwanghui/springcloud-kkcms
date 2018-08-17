/**
 * Copyright (c) 2010-2015 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2015湖南蚁坊软件有限公司。保留所有权利。
 */
package com.kk.wx.cp.controller;

import com.kk.wx.cp.service.IWxDepartService;
import com.kk.wx.cp.service.IWxUserService;
import com.kk.wx.cp.service.impl.WxDepartServiceImpl;
import com.kk.wx.cp.service.impl.WxUserServiceImpl;
import com.kk.wx.cp.utils.CipherUtils;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpMessageSendResult;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.List;

/**
 * 后台业务调用服务
 * WxSendMsgController
 */
@RestController
@RequestMapping("/wx/cp")
public class WxServiceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    @Qualifier(value = "CpService")
    private WxCpService wxService;

    @Autowired
    private WxUserServiceImpl userService;

    @Autowired
    private WxDepartServiceImpl departmentService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/sendMsg")
    public WxCpMessageSendResult sendMsg(@RequestHeader(value = "Token", required = true) String token,
                                         @RequestBody WxCpMessage cpMessage) throws WxErrorException {

        cpMessage.setAgentId(this.wxService.getWxCpConfigStorage().getAgentId());
        return this.wxService.messageSend(cpMessage);
    }


    //------------------------通讯录：部门接口-----------------------

    /**
     * 查询所有部门
     *
     * @param token
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/department/listAll")
    public List<WxCpDepart> departmentCreateListAll() throws WxErrorException {
        return this.departmentService.list(1);
    }

    /**
     * 创建部门
     *
     * @param token
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/department/create")
    public Integer departmentCreate(@RequestHeader(value = "Token", required = true) String token,
                                    @RequestBody WxCpDepart wxCpDepart) throws WxErrorException {

        return this.departmentService.create(wxCpDepart);
    }

    /**
     * 更新部门
     *
     * @param token
     * @param wxCpDepart
     * @throws WxErrorException
     */
    @PostMapping("/department/update")
    public void departmentUpdate(@RequestHeader(value = "Token", required = true) String token,
                                 @RequestBody WxCpDepart wxCpDepart) throws WxErrorException {

        this.departmentService.update(wxCpDepart);
    }

    /**
     * 删除部门
     *
     * @param token
     * @param departId
     * @throws WxErrorException
     */
    @PostMapping("/department/delete")
    public void departmentUpdate(@RequestHeader(value = "Token", required = true) String token,
                                 @RequestParam(value = "departId", required = true) Integer departId) throws WxErrorException {

        this.departmentService.delete(departId);
    }

    //------------------------通讯录：部门接口-----------------------


    //------------------------通讯录：成员接口-----------------------

    /**
     * 获取部门成员(详情)
     *
     * @param token
     * @param departId   必填。部门id
     * @param fetchChild 非必填。1/0：是否递归获取子部门下面的成员
     * @param status     非必填。0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
     * @throws WxErrorException
     */
    @PostMapping("/user/listByDepartment")
    public List<WxCpUser> userListByDepartment(@RequestHeader(value = "Token", required = true) String token,
                                               @RequestParam(value = "departId", required = true) Integer departId,
                                               @RequestParam(value = "fetchChild", required = false) boolean fetchChild,
                                               @RequestParam(value = "status", required = false) Integer status) throws WxErrorException {
        if (userService.tbWxUserFromWeb()) {
            return this.userService.listByDepartment(departId, fetchChild, status);
        } else throw new WxErrorException(getTokenError());


    }


    /**
     * 获取部门成员
     *
     * @param token
     * @param departId
     * @param fetchChild
     * @param status
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/user/listSimpleByDepartment")
    public List<WxCpUser> userlistSimpleByDepartment(@RequestHeader(value = "Token", required = true) String token,
                                                     @RequestParam(value = "departId", required = true) Integer departId,
                                                     @RequestParam(value = "fetchChild", required = false) boolean fetchChild,
                                                     @RequestParam(value = "status", required = false) Integer status) throws WxErrorException {
        return this.userService.listSimpleByDepartment(departId, fetchChild, status);

    }

    /**
     * 创建成员
     *
     * @param token
     * @param user
     * @throws WxErrorException
     */
    @PostMapping("/user/create")
    public void userCreate(@RequestHeader(value = "Token", required = true) String token,
                           @RequestBody WxCpUser user) throws WxErrorException {
        this.userService.create(user);

    }


    /**
     * 更新成员
     *
     * @param token
     * @param user
     * @throws WxErrorException
     */
    @PostMapping("/user/update")
    public void userUpdate(@RequestHeader(value = "Token", required = true) String token,
                           @RequestBody WxCpUser user) throws WxErrorException {
        this.userService.update(user);

    }

    /**
     * 删除成员
     *
     * @param token
     * @param userIds
     * @throws WxErrorException
     */
    @PostMapping("/user/delete")
    public void userDelete(@RequestHeader(value = "Token", required = true) String token,
                           @RequestParam(value = "userIds", required = true) String[] userIds) throws WxErrorException {
        this.userService.delete(userIds);

    }

    /**
     * 获取用户
     *
     * @param token
     * @param userid
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/user/getById")
    public WxCpUser userDelete(@RequestHeader(value = "Token", required = true) String token,
                               @RequestParam(value = "userid", required = true) String userid) throws WxErrorException {
        return this.userService.getById(userid);
    }

    //------------------------通讯录：成员接口-----------------------


    private WxError getTokenError() {
        //WxError error = new WxError();
        WxError error = null;
        error.setErrorMsg("token error");
        return error;
    }


    @GetMapping("/signature")
    @ResponseBody
    public WxJsapiSignature signature(String url) throws WxErrorException {
        return wxService.createJsapiSignature(url);
    }


}

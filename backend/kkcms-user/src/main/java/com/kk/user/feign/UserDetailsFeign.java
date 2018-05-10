package com.kk.user.feign;


import com.kk.common.vo.KUserVo;
import com.kk.common.vo.PermissionInfo;
import com.kk.common.vo.SocialVo;
import com.kk.user.entity.KUser;

import com.kk.user.entity.SysConnection;
import com.kk.user.po.ElementVo;
import com.kk.user.service.ISysConnectionService;
import com.kk.user.service.ISysRoleService;
import com.kk.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by msi- on 2018/1/19.
 * 通过用户名获取用户的基本信息
 */
@RestController
@RequestMapping("userDetailsFeign")
public class UserDetailsFeign {
    @Autowired
    private IUserService userService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysConnectionService sysConnectionService;

    @RequestMapping(value = "/user/username/{username}", method = RequestMethod.POST)
    @ApiOperation(value = "认证：通过用户名查找用户认证信息接口")
    public KUserVo getByUsername(@PathVariable("username") String username) {
        KUserVo kUserVo = null;
        KUser kUser = userService.getByUsername(username);
        if (kUser != null) {
            kUserVo = new KUserVo();
            BeanUtils.copyProperties(kUser, kUserVo);
            //用户名联合查询用户的角色信息
            kUserVo.setRoleList(Arrays.asList(sysRoleService.getCodeByUsername(username)));
        }
        return kUserVo;
    }

    @RequestMapping(value = "/user/mobile/{mobile}", method = RequestMethod.POST)
    @ApiOperation(value = "认证：通过手机号码查找用户认证信息接口")
    public KUserVo getByMobile(@PathVariable("mobile") String mobile) {
        KUserVo kUserVo = null;
        KUser kUser = userService.getByMobile(mobile);
        if (kUser != null) {
            kUserVo = new KUserVo();
            BeanUtils.copyProperties(kUser, kUserVo);
            //用户名联合查询用户的角色信息
            kUserVo.setRoleList(Arrays.asList(sysRoleService.getCodeByUsername(kUser.getUsername())));
        }
        return kUserVo;
    }

    @RequestMapping(value = "/social/qq/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "社交：通过userId查找用户认证信息接口")
    public SocialVo getByUserId(@PathVariable("userId") String userId) {
        SocialVo socialVo = null;
        SysConnection sysConnection = sysConnectionService.getByUserId(userId);
        if (sysConnection != null) {
            BeanUtils.copyProperties(sysConnection, socialVo);
            System.out.print("查找社交：" + socialVo.toString());
        }
        return socialVo;
    }

    /*@RequestMapping(value = "/permissions/roles/{roles}", method = RequestMethod.POST)
    @ApiOperation(value = "授权：角色查找接口")
    public Set<PermissionInfo> findPermissionInfoByRoles(@PathVariable("roles") String roles) {
        Set<PermissionInfo> resultSet = new HashSet<>();
        try {
            String[] roleList = roles.split(";");
            for (int i = 0; i < roleList.length; i++) {
                String role = roleList[i];
                List<ElementVo> tElements = sysElementService.getListByRole(role);
                tElements.forEach(tElement -> {
                    PermissionInfo permissionInfo = new PermissionInfo();
                    permissionInfo.setCode(tElement.getCode());
                    permissionInfo.setMethod(tElement.getMethod());
                    permissionInfo.setType(tElement.getElementType());
                    permissionInfo.setUri(tElement.getUri());
                    resultSet.add(permissionInfo);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }*/
}

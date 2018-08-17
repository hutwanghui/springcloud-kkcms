package com.kk.modules.sys.oauth2.impl;

import com.kk.common.utils.Constant;
import com.kk.modules.sys.dao.SysMenuDao;
import com.kk.modules.sys.dao.SysUserDao;
import com.kk.modules.sys.entity.SysMenuEntity;
import com.kk.modules.sys.entity.SysUserEntity;
import com.kk.modules.sys.oauth2.IRbacService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by msi- on 2018/4/5.
 */
@Component("rbacService")
public class RbacServiceImpl implements IRbacService {

    private static Logger logger = LoggerFactory.getLogger(RbacServiceImpl.class);
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            //读取用户所有的URL,这里通过数据库中获取
            Set<String> urls = new HashSet<>();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return false;
    }

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public Set<String> getUserPermissions(Integer userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
            logger.info("管理员" + userId + "登陆，获取所有菜单以及权限使用");
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
            logger.info("非管理员" + userId + "登陆，获取指定菜单以及权限使用" + permsList.size());
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserEntity queryUser(Integer userId) {
        return sysUserDao.selectById(userId);
    }
}

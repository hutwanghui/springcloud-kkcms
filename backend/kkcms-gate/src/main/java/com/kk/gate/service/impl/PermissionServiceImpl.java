package com.kk.gate.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kk.gate.feign.IUserAuthFeign;
import com.kk.gate.service.PermissionService;
import com.kk.common.vo.PermissionInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by msi- on 2018/1/19.
 * 权限认证
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Autowired
    private IUserAuthFeign userAuthFeign;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        logger.info("kkcms:hasPermission");
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        logger.info("kkcms进行授权:grantedAuthorityList:{}", JSONObject.toJSON(grantedAuthorityList));
        boolean hasPermission = false;

        if (principal != null) {
            Set<String> roleSet = new HashSet<>();
            grantedAuthorityList.forEach(grantedAuthority -> roleSet.add(grantedAuthority.getAuthority()));
            String roles = StringUtils.join(roleSet,";");
            Set<PermissionInfo> permissionInfos = userAuthFeign.findPermissionInfoByRoles(roles);
            logger.info("kkcms:PersissionInfos：{}", JSONObject.toJSON(permissionInfos));
            logger.info("kkcms:request.getRequestURI()：{}", request.getRequestURI());
            for (PermissionInfo permissionInfo : permissionInfos) {
                if (antPathMatcher.match(permissionInfo.getUri(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(permissionInfo.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        logger.info("kkcms:hasPermission:{}", hasPermission);
        return hasPermission;
    }
}

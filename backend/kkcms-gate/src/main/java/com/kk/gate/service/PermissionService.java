package com.kk.gate.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by msi- on 2018/1/19.
 * 用于从数据库中读取角色信息进行授权
 */
public interface PermissionService{
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}

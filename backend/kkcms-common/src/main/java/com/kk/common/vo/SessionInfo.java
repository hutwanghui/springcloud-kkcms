package com.kk.common.vo;

import com.kk.common.vo.KUserVo;

import java.io.Serializable;

public class SessionInfo implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 登录名
     */
    private String username;

    /**
     * 用户基本信息
     */
    private KUserVo kUserVo;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

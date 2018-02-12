package com.kk.user.entity;

import javax.persistence.*;

@Table(name = "sys_role_user")
public class SysRoleUser {
    @Id
    private Integer id;

    @Column(name = "sys_user_id")
    private Integer sysUserId;

    @Column(name = "sys_role_id")
    private Integer sysRoleId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return sys_user_id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * @param sysUserId
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return sys_role_id
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * @param sysRoleId
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }
}
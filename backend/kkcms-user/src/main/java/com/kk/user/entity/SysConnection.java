package com.kk.user.entity;

import javax.persistence.*;

@Table(name = "sys_userconnection")
public class SysConnection {
    /**
     * 业务系统的用户ID
     */
    @Id
    @Column(name = "userId")
    private String userid;

    /**
     * 服务商的ID
     */
    @Id
    @Column(name = "providerId")
    private String providerid;

    /**
     * 服务提供商的用户ID
     */
    @Id
    @Column(name = "providerUserId")
    private String provideruserid;

    /**
     * 等级
     */
    private Integer rank;

    /**
     * 适配器转换后：用户昵称
     */
    @Column(name = "displayName")
    private String displayname;

    /**
     * 适配器转换后：主页
     */
    @Column(name = "profileUrl")
    private String profileurl;

    /**
     * 适配器转换后：头像
     */
    @Column(name = "imageUrl")
    private String imageurl;

    /**
     * 存放令牌
     */
    @Column(name = "accessToken")
    private String accesstoken;

    private String secret;

    @Column(name = "refreshToken")
    private String refreshtoken;

    /**
     * 过期时间
     */
    @Column(name = "expireTime")
    private Long expiretime;

    /**
     * 获取业务系统的用户ID
     *
     * @return userId - 业务系统的用户ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置业务系统的用户ID
     *
     * @param userid 业务系统的用户ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取服务商的ID
     *
     * @return providerId - 服务商的ID
     */
    public String getProviderid() {
        return providerid;
    }

    /**
     * 设置服务商的ID
     *
     * @param providerid 服务商的ID
     */
    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    /**
     * 获取服务提供商的用户ID
     *
     * @return providerUserId - 服务提供商的用户ID
     */
    public String getProvideruserid() {
        return provideruserid;
    }

    /**
     * 设置服务提供商的用户ID
     *
     * @param provideruserid 服务提供商的用户ID
     */
    public void setProvideruserid(String provideruserid) {
        this.provideruserid = provideruserid;
    }

    /**
     * 获取等级
     *
     * @return rank - 等级
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 设置等级
     *
     * @param rank 等级
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 获取适配器转换后：用户昵称
     *
     * @return displayName - 适配器转换后：用户昵称
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * 设置适配器转换后：用户昵称
     *
     * @param displayname 适配器转换后：用户昵称
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     * 获取适配器转换后：主页
     *
     * @return profileUrl - 适配器转换后：主页
     */
    public String getProfileurl() {
        return profileurl;
    }

    /**
     * 设置适配器转换后：主页
     *
     * @param profileurl 适配器转换后：主页
     */
    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    /**
     * 获取适配器转换后：头像
     *
     * @return imageUrl - 适配器转换后：头像
     */
    public String getImageurl() {
        return imageurl;
    }

    /**
     * 设置适配器转换后：头像
     *
     * @param imageurl 适配器转换后：头像
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * 获取存放令牌
     *
     * @return accessToken - 存放令牌
     */
    public String getAccesstoken() {
        return accesstoken;
    }

    /**
     * 设置存放令牌
     *
     * @param accesstoken 存放令牌
     */
    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    /**
     * @return secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return refreshToken
     */
    public String getRefreshtoken() {
        return refreshtoken;
    }

    /**
     * @param refreshtoken
     */
    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    /**
     * 获取过期时间
     *
     * @return expireTime - 过期时间
     */
    public Long getExpiretime() {
        return expiretime;
    }

    /**
     * 设置过期时间
     *
     * @param expiretime 过期时间
     */
    public void setExpiretime(Long expiretime) {
        this.expiretime = expiretime;
    }
}
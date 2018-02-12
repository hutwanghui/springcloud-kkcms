package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

public class User {
    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * name
     */
    private String name;

    /**
     * birthday
     */
    private String birthday;

    /**
     * address
     */
    private String address;

    /**
     * mobilePhone
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * sex
     */
    private String sex;

    /**
     * status
     */
    private String status;

    /**
     * createTime
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * updateTime
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 标签
     */
    private String tag;

    /**
     * 头像url
     */
    private String head;

    /**
     * 联系人列表
     */
    @Column(name = "contactIdList")
    private String contactidlist;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取username
     *
     * @return username - username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置username
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取password
     *
     * @return password - password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取name
     *
     * @return name - name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取birthday
     *
     * @return birthday - birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置birthday
     *
     * @param birthday birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取address
     *
     * @return address - address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置address
     *
     * @param address address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取mobilePhone
     *
     * @return mobile - mobilePhone
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置mobilePhone
     *
     * @param mobile mobilePhone
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取email
     *
     * @return email - email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取sex
     *
     * @return sex - sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置sex
     *
     * @param sex sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取status
     *
     * @return status - status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取createTime
     *
     * @return create_time - createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime
     *
     * @param createTime createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取updateTime
     *
     * @return update_time - updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取标签
     *
     * @return tag - 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取头像url
     *
     * @return head - 头像url
     */
    public String getHead() {
        return head;
    }

    /**
     * 设置头像url
     *
     * @param head 头像url
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * 获取联系人列表
     *
     * @return contactIdList - 联系人列表
     */
    public String getContactidlist() {
        return contactidlist;
    }

    /**
     * 设置联系人列表
     *
     * @param contactidlist 联系人列表
     */
    public void setContactidlist(String contactidlist) {
        this.contactidlist = contactidlist;
    }
}
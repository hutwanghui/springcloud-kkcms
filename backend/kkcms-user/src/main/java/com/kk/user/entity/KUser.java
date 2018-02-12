package com.kk.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user")
public class KUser {
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
    @Column(name = "mobile")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * updateTime
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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
     * 获取mobile
     *
     * @return mobile - mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置mobile
     *
     * @param mobile mobile
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

    @Override
    public String toString() {
        return "KUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", mobilePhone='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "praise")
public class Parise {
    /**
     * 动态id
     */
    @Id
    private Long id;

    /**
     * 唯一标识
     */
    @Column(name = "momentId")
    private Long momentid;

    /**
     * 用户id
     */
    @Column(name = "userId")
    private Long userid;

    /**
     * 点赞时间
     */
    private Date date;

    /**
     * 获取动态id
     *
     * @return id - 动态id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置动态id
     *
     * @param id 动态id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取唯一标识
     *
     * @return momentId - 唯一标识
     */
    public Long getMomentid() {
        return momentid;
    }

    /**
     * 设置唯一标识
     *
     * @param momentid 唯一标识
     */
    public void setMomentid(Long momentid) {
        this.momentid = momentid;
    }

    /**
     * 获取用户id
     *
     * @return userId - 用户id
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * 设置用户id
     *
     * @param userid 用户id
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * 获取点赞时间
     *
     * @return date - 点赞时间
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置点赞时间
     *
     * @param date 点赞时间
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
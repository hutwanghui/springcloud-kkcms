package com.kk.api.entity;

import com.kk.common.util.ObjectUtil;

import javax.persistence.*;

public class Comment extends BaseModel {

    @Column(name = "toId")
    private Long toid;

    @Column(name = "momentId")
    private Long momentid;

    private String content;

    @Transient
    private User user;

    @Transient
    private User touser;

    @Transient
    private Moment moment;

    public CommentItem getBase() {
        return ObjectUtil.copyProperties(this, CommentItem.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTouser() {
        return touser;
    }

    public void setTouser(User touser) {
        this.touser = touser;
    }

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    /**
     * @return toId
     */
    public Long getToid() {
        return toid;
    }

    /**
     * @param toid
     */
    public void setToid(Long toid) {
        this.toid = toid;
    }

    /**
     * @return momentId
     */
    public Long getMomentid() {
        return momentid;
    }

    /**
     * @param momentid
     */
    public void setMomentid(Long momentid) {
        this.momentid = momentid;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (getToid() != null ? !getToid().equals(comment.getToid()) : comment.getToid() != null) return false;
        if (getMomentid() != null ? !getMomentid().equals(comment.getMomentid()) : comment.getMomentid() != null)
            return false;
        if (getContent() != null ? !getContent().equals(comment.getContent()) : comment.getContent() != null)
            return false;
        if (getUser() != null ? !getUser().equals(comment.getUser()) : comment.getUser() != null) return false;
        if (getTouser() != null ? !getTouser().equals(comment.getTouser()) : comment.getTouser() != null) return false;
        return getMoment() != null ? getMoment().equals(comment.getMoment()) : comment.getMoment() == null;
    }

    @Override
    public int hashCode() {
        int result = getToid() != null ? getToid().hashCode() : 0;
        result = 31 * result + (getMomentid() != null ? getMomentid().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getTouser() != null ? getTouser().hashCode() : 0);
        result = 31 * result + (getMoment() != null ? getMoment().hashCode() : 0);
        return result;
    }
}
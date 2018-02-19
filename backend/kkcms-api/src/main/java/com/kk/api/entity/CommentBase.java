package com.kk.api.entity;

import javax.persistence.Transient;

/**
 * Created by msi- on 2018/2/19.
 */
public class CommentBase extends BaseModel {
    private String content;
    private User user;
    private User touser;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}

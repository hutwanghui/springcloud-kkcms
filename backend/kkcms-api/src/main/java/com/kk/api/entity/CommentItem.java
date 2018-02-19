package com.kk.api.entity;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public class CommentItem extends BaseModel {
    /*private Comment comment;*/
    private String content;
    private User user;
    private User touser;
/*    private List<CommentItem> childList;*/

    /*public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }*/

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

//    public List<CommentItem> getChildList() {
//        return childList;
//    }
//
//    public void setChildList(List<CommentItem> childList) {
//        this.childList = childList;
//    }
}

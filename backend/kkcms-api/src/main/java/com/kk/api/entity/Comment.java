package com.kk.api.entity;

import javax.persistence.*;

public class Comment extends BaseModel {

    @Column(name = "toId")
    private Long toid;

    @Column(name = "momentId")
    private Long momentid;

    private String content;

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
}
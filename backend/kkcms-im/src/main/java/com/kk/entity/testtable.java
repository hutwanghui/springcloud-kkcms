package com.kk.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class testtable {
    @Override
    public String toString() {
        return "testtable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createtime=" + createtime +
                '}';
    }

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATETIME")
    private Date createtime;

    /**
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return CREATETIME
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
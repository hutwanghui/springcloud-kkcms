package com.kk.modules.hadoop.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;
import javax.persistence.*;
@TableName("shelltask")
public class ShellTask {
    @TableId
    private Integer id;

    @Column(name = "shell_name")
    private String shellName;

    @Column(name = "shell_path")
    private String shellPath;

    @Column(name = "shell_desc")
    private String shellDesc;

    @Column(name = "update_time")
    private Date updateTime;

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
     * @return shell_name
     */
    public String getShellName() {
        return shellName;
    }

    /**
     * @param shellName
     */
    public void setShellName(String shellName) {
        this.shellName = shellName;
    }

    /**
     * @return shell_path
     */
    public String getShellPath() {
        return shellPath;
    }

    /**
     * @param shellPath
     */
    public void setShellPath(String shellPath) {
        this.shellPath = shellPath;
    }

    /**
     * @return shell_desc
     */
    public String getShellDesc() {
        return shellDesc;
    }

    /**
     * @param shellDesc
     */
    public void setShellDesc(String shellDesc) {
        this.shellDesc = shellDesc;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
package com.kk.modules.attendence.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.util.Date;

@TableName("attendance_completed")
public class Atd_complated {
    @TableId
    private Integer id;

    @Column(name = "attendance_rule_id")
    private String attendanceRuleId;

    @Column(name = "attendance_time")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date attendanceTime;

    @Column(name = "attendance_address")
    private String attendanceAddress;

    @Column(name = "attendance_type")
    private Integer attendanceType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

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
     * @return attendance_rule_id
     */
    public String getAttendanceRuleId() {
        return attendanceRuleId;
    }

    /**
     * @param attendanceRuleId
     */
    public void setAttendanceRuleId(String attendanceRuleId) {
        this.attendanceRuleId = attendanceRuleId;
    }

    /**
     * @return attendance_time
     */
    public Date getAttendanceTime() {
        return attendanceTime;
    }

    /**
     * @param attendanceTime
     */
    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    /**
     * @return attendance_address
     */
    public String getAttendanceAddress() {
        return attendanceAddress;
    }

    /**
     * @param attendanceAddress
     */
    public void setAttendanceAddress(String attendanceAddress) {
        this.attendanceAddress = attendanceAddress;
    }

    /**
     * @return attendance_type
     */
    public Integer getAttendanceType() {
        return attendanceType;
    }

    /**
     * @param attendanceType
     */
    public void setAttendanceType(Integer attendanceType) {
        this.attendanceType = attendanceType;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
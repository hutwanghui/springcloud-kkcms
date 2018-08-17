package com.kk.modules.attendence.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.util.Date;

@TableName("attendance_rule")
public class Atd_rule {
    @TableId
    private Integer id;

    @Column(name = "attendance_name")
    private String attendanceName;

    @Column(name = "attendance_week")
    private String attendanceWeek;

    @Column(name = "attendance_time_begin")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date attendanceTimeBegin;

    @Column(name = "attendance_time_end")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date attendanceTimeEnd;

    @Column(name = "attendance_address_jing")
    private String attendanceAddressJing;

    @Column(name = "attendance_address_wei")
    private String attendanceAddressWei;

    @Column(name = "attendance_Administrators")
    private String attendanceAdministrators;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "depart_id")
    private String departId;

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
     * @return attendance_name
     */
    public String getAttendanceName() {
        return attendanceName;
    }

    /**
     * @param attendanceName
     */
    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    /**
     * @return attendance_week
     */
    public String getAttendanceWeek() {
        return attendanceWeek;
    }

    /**
     * @param attendanceWeek
     */
    public void setAttendanceWeek(String attendanceWeek) {
        this.attendanceWeek = attendanceWeek;
    }

    /**
     * @return attendance_time_begin
     */
    public Date getAttendanceTimeBegin() {
        return attendanceTimeBegin;
    }

    /**
     * @param attendanceTimeBegin
     */
    public void setAttendanceTimeBegin(Date attendanceTimeBegin) {
        this.attendanceTimeBegin = attendanceTimeBegin;
    }

    /**
     * @return attendance_time_end
     */
    public Date getAttendanceTimeEnd() {
        return attendanceTimeEnd;
    }

    /**
     * @param attendanceTimeEnd
     */
    public void setAttendanceTimeEnd(Date attendanceTimeEnd) {
        this.attendanceTimeEnd = attendanceTimeEnd;
    }

    /**
     * @return attendance_address_jing
     */
    public String getAttendanceAddressJing() {
        return attendanceAddressJing;
    }

    /**
     * @param attendanceAddressJing
     */
    public void setAttendanceAddressJing(String attendanceAddressJing) {
        this.attendanceAddressJing = attendanceAddressJing;
    }

    /**
     * @return attendance_address_wei
     */
    public String getAttendanceAddressWei() {
        return attendanceAddressWei;
    }

    /**
     * @param attendanceAddressWei
     */
    public void setAttendanceAddressWei(String attendanceAddressWei) {
        this.attendanceAddressWei = attendanceAddressWei;
    }

    /**
     * @return attendance_Administrators
     */
    public String getAttendanceAdministrators() {
        return attendanceAdministrators;
    }

    /**
     * @param attendanceAdministrators
     */
    public void setAttendanceAdministrators(String attendanceAdministrators) {
        this.attendanceAdministrators = attendanceAdministrators;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    @Override
    public String toString() {
        return "Atd_rule{" +
                "id=" + id +
                ", attendanceName='" + attendanceName + '\'' +
                ", attendanceWeek='" + attendanceWeek + '\'' +
                ", attendanceTimeBegin=" + attendanceTimeBegin +
                ", attendanceTimeEnd=" + attendanceTimeEnd +
                ", attendanceAddressJing='" + attendanceAddressJing + '\'' +
                ", attendanceAddressWei='" + attendanceAddressWei + '\'' +
                ", attendanceAdministrators='" + attendanceAdministrators + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", departId='" + departId + '\'' +
                '}';
    }
}
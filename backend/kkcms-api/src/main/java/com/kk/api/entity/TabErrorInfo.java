package com.kk.api.entity;

import java.util.Date;

public class TabErrorInfo {
    private String nsrsbh;
    private String mobile;
    private String errorContent;
    private String errorType;
    private Date createAt;
    private String nsrmc;
    private String hyDm;
    private String zcdz;
    private String jdxzDm;

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh == null ? null : nsrsbh.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent == null ? null : errorContent.trim();
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType == null ? null : errorType.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getHyDm() {
        return hyDm;
    }

    public void setHyDm(String hyDm) {
        this.hyDm = hyDm;
    }

    public String getZcdz() {
        return zcdz;
    }

    public void setZcdz(String zcdz) {
        this.zcdz = zcdz;
    }

    public String getJdxzDm() {
        return jdxzDm;
    }

    public void setJdxzDm(String jdxzDm) {
        this.jdxzDm = jdxzDm;
    }

    public TabErrorInfo(String nsrsbh, String mobile, String errorContent, String errorType, Date createAt, String nsrmc, String hyDm, String zcdz, String jdxzDm) {
        this.nsrsbh = nsrsbh;
        this.mobile = mobile;
        this.errorContent = errorContent;
        this.errorType = errorType;
        this.createAt = createAt;
        this.nsrmc = nsrmc;
        this.hyDm = hyDm;
        this.zcdz = zcdz;
        this.jdxzDm = jdxzDm;
    }
}
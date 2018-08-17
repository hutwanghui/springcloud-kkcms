package com.kk.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;
import javax.persistence.*;

@TableName("lagou")
public class LgArticle {
    @TableId
    @Column(name = "url_object_id")
    private String urlObjectId;

    private String url;

    private String title;

    private String salary;

    @Column(name = "job_city")
    private String jobCity;

    @Column(name = "work_years")
    private String workYears;

    @Column(name = "degree_need")
    private String degreeNeed;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "publish_time")
    private String publishTime;

    private String tags;

    @Column(name = "job_advantage")
    private String jobAdvantage;

    @Column(name = "job_addr")
    private String jobAddr;

    @Column(name = "company_url")
    private String companyUrl;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "crawl_time")
    private Date crawlTime;

    @Column(name = "crawl_update_time")
    private Date crawlUpdateTime;

    @Column(name = "job_desc")
    private String jobDesc;

    /**
     * @return url_object_id
     */
    public String getUrlObjectId() {
        return urlObjectId;
    }

    /**
     * @param urlObjectId
     */
    public void setUrlObjectId(String urlObjectId) {
        this.urlObjectId = urlObjectId;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * @return job_city
     */
    public String getJobCity() {
        return jobCity;
    }

    /**
     * @param jobCity
     */
    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    /**
     * @return work_years
     */
    public String getWorkYears() {
        return workYears;
    }

    /**
     * @param workYears
     */
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /**
     * @return degree_need
     */
    public String getDegreeNeed() {
        return degreeNeed;
    }

    /**
     * @param degreeNeed
     */
    public void setDegreeNeed(String degreeNeed) {
        this.degreeNeed = degreeNeed;
    }

    /**
     * @return job_type
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * @param jobType
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * @return publish_time
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * @param publishTime
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * @return tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return job_advantage
     */
    public String getJobAdvantage() {
        return jobAdvantage;
    }

    /**
     * @param jobAdvantage
     */
    public void setJobAdvantage(String jobAdvantage) {
        this.jobAdvantage = jobAdvantage;
    }

    /**
     * @return job_addr
     */
    public String getJobAddr() {
        return jobAddr;
    }

    /**
     * @param jobAddr
     */
    public void setJobAddr(String jobAddr) {
        this.jobAddr = jobAddr;
    }

    /**
     * @return company_url
     */
    public String getCompanyUrl() {
        return companyUrl;
    }

    /**
     * @param companyUrl
     */
    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    /**
     * @return company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return crawl_time
     */
    public Date getCrawlTime() {
        return crawlTime;
    }

    /**
     * @param crawlTime
     */
    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

    /**
     * @return crawl_update_time
     */
    public Date getCrawlUpdateTime() {
        return crawlUpdateTime;
    }

    /**
     * @param crawlUpdateTime
     */
    public void setCrawlUpdateTime(Date crawlUpdateTime) {
        this.crawlUpdateTime = crawlUpdateTime;
    }

    /**
     * @return job_desc
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     * @param jobDesc
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
}
package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_action")
public class UserAction {
    private String id;

    private String uid;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "behavior_type")
    private String behaviorType;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "visit_date")
    private Date visitDate;

    private String province;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return item_id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return behavior_type
     */
    public String getBehaviorType() {
        return behaviorType;
    }

    /**
     * @param behaviorType
     */
    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    /**
     * @return item_category
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * @param itemCategory
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * @return visit_date
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * @param visitDate
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * @return province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }
}
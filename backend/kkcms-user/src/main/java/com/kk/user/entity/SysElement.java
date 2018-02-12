package com.kk.user.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_element")
public class SysElement {
    @Id
    private Integer id;

    @Column(name = "element_type")
    private Integer elementType;

    @Column(name = "menu_id")
    private Integer menuId;

    private String code;

    @Column(name = "element_name")
    private String elementName;

    private String uri;

    private String method;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return element_type
     */
    public Integer getElementType() {
        return elementType;
    }

    /**
     * @param elementType
     */
    public void setElementType(Integer elementType) {
        this.elementType = elementType;
    }

    /**
     * @return menu_id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return element_name
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * @param elementName
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * @return uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
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
}
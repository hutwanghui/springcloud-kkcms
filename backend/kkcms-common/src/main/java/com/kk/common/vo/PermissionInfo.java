package com.kk.common.vo;

import java.io.Serializable;

/**
 * Created by msi- on 2018/1/19.
 */
public class PermissionInfo implements Serializable {
    private String code;
    private Integer type;
    private String uri;
    private String method;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
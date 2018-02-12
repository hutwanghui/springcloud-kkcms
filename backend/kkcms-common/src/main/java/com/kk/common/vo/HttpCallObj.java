package com.kk.common.vo;

/**
 * Created by msi- on 2018/1/23.
 */
public class HttpCallObj {
    private int content;
    private String cookieStr;

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public HttpCallObj(int content, String cookieStr) {
        this.content = content;
        this.cookieStr = cookieStr;
    }

    public HttpCallObj() {
    }
}

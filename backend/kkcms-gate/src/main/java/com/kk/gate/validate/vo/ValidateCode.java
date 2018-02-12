package com.kk.gate.validate.vo;

import com.kk.common.constant.CommonConstant;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by msi- on 2018/1/23.
 * 短信验证码
 */
public class ValidateCode implements Serializable {
    //验证码
    private String code;
    //验证码过期时间
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }


    public ValidateCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}

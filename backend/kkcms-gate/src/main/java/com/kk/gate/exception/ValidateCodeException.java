package com.kk.gate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by msi- on 2018/1/24.
 */
public class ValidateCodeException extends AuthenticationException {
    private String code;

    public ValidateCodeException(String explanation) {
        super(explanation);
    }

    public ValidateCodeException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

package com.kk.gate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by msi- on 2018/1/24.
 */
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String explanation) {
        super(explanation);
    }
}

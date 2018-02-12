package com.kk.gate.validate.code.sms.provider;

import com.kk.gate.service.impl.UserDetailsServiceImpl;
import com.kk.gate.validate.code.sms.entity.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by msi- on 2018/1/25.
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    public UserDetailsServiceImpl getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 调取UserDetailsService获取封装了用户信息的UserDetails，拿获取的用户信息重新组装成一个Authentication，即以认证成功！
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        //System.out.print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + smsAuthenticationToken.toString());
        UserDetails userDetails = userDetailsService.loadUserByMobile((String) smsAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //认证通过的token
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        //将未认证的token中的details信息copy回已经认证的token，因为以前的这个未认证的token里面还有request的其他内容，不可以丢弃
        authenticationResult.setDetails(smsAuthenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * AuthenticationManager就是根据support这个方法来判断调用哪个provider进行判断的
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //返回一个是否是SmsAuthenticationToken类型的布尔值
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

package com.kk.gate.config.properties.security;

/**
 * Created by msi- on 2018/1/23.
 */
public class BrowserProperties {
    private String loginPage;
    private String sessionInvalidPage;
    private int maximumSessions;
    private boolean isPreventsLogin;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isPreventsLogin() {
        return isPreventsLogin;
    }

    public void setPreventsLogin(boolean preventsLogin) {
        isPreventsLogin = preventsLogin;
    }

    public String getSessionInvalidPage() {
        return sessionInvalidPage;
    }

    public void setSessionInvalidPage(String sessionInvalidPage) {
        this.sessionInvalidPage = sessionInvalidPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}

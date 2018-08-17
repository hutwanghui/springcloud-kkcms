package com.kk.modules.sys.oauth2;

import com.kk.common.vo.KUserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 * implements UserDetails 用于登录时 @AuthenticationPrincipal 标签取值
 */
public class UserInfo implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private List<String> roleList = new ArrayList<>();

    public UserInfo(KUserVo kUserVo) {
        this.username = kUserVo.getUsername();
        this.password = kUserVo.getPassword();
        roleList = kUserVo.getRoleList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        roleList.forEach(role ->authorityList.add(new SimpleGrantedAuthority(role)));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleList=" + roleList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo) o;
        if (getUsername() != null ? !getUsername().equals(userInfo.getUsername()) : userInfo.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(userInfo.getPassword()) : userInfo.getPassword() != null)
            return false;
        return roleList != null ? roleList.equals(userInfo.roleList) : userInfo.roleList == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (roleList != null ? roleList.hashCode() : 0);
        return result;
    }
}

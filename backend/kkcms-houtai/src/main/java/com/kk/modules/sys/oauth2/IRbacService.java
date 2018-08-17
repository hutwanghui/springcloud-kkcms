package com.kk.modules.sys.oauth2;

import com.kk.modules.sys.entity.SysUserEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by msi- on 2018/4/5.
 */
public interface IRbacService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
    public Set<String> getUserPermissions(Integer userId);
    public SysUserEntity queryUser(Integer userId);
}

package com.kk.user.serviceTest;

import com.kk.user.service.ISysAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by msi- on 2018/1/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorityTest {
    @Autowired
    private ISysAuthorityService sysAuthorityService;

    @Test
    public void getAuthority() {
        System.out.print("kkkkk："+sysAuthorityService.getAuthority(1));

    }
}

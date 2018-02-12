package com.kk.user.serviceTest;

import com.kk.common.service.BaseService;
import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.user.entity.SysRole;
import com.kk.user.service.ISysRoleService;
import com.kk.user.service.impl.SysRoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


/**
 * Created by msi- on 2018/1/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleServiceImplTest {

    @Qualifier("sysRoleServiceImpl")
    @Autowired
    private BaseService baseService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Test
    public void getSysRoleList() {
        System.out.print("==========================" + Arrays.asList(sysRoleService.getCodeByUsername("admin")).toString());
    }
}
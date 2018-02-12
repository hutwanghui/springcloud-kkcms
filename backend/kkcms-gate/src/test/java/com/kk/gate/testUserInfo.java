package com.kk.gate;

import com.kk.common.vo.KUserVo;
import com.kk.gate.vo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi- on 2018/1/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testUserInfo {
    @Test
    public void objectChange() {
        List arrayList = new ArrayList();
        arrayList.add("ADMIN");
        arrayList.add("USER");
        KUserVo kUserVo = new KUserVo("admin123", "123456", arrayList);
        UserInfo userInfo = new UserInfo(kUserVo);
        System.out.print(userInfo.toString());
    }
}

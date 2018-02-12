/*
package com.kk.user.serviceTest;

import com.ctc.wstx.util.DataUtil;
import com.kk.common.service.BaseService;
import com.kk.common.util.DateUtil;
import com.kk.common.util.LogUtil;
import com.kk.common.util.ObjectUtil;
import com.kk.user.entity.KUser;
import com.kk.user.service.ISysRoleService;
import com.kk.user.service.IUserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

*/
/**
 * Created by msi- on 2018/1/18.
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private IUserService userService;
    @Qualifier("userServiceImpl")
    @Autowired
    private BaseService baseService;

    @Test
    public void getByUsername() throws Exception {
        KUser kUser = userService.getByUsername("admin");
        System.out.print("测试getByUsername：" + kUser.toString());
    }

    @Test
    public void getByKey_name() throws Exception {
        KUser user_name = new KUser();
        user_name.setName("王");
        List<KUser> list = userService.getByKey(user_name);
        System.out.print("测试getKey：" + list.toString());
    }

    @Test
    public void getByKey_username() {
        KUser user_username = new KUser();
        user_username.setUsername("Admin");
        List<KUser> list = userService.getByKey(user_username);
        System.out.print("测试getKey：" + list.toString());
    }

    @Test
    public void getByKey_mobile_phone() {
        KUser user_mobile_phone = new KUser();
        user_mobile_phone.setMobilePhone("18042261719");
        List<KUser> list = userService.getByKey(user_mobile_phone);
        System.out.print("测试getKey：" + list.toString());
    }

    @Test
    public void getByKey_no_condition() {
        KUser kUser = new KUser();
        List<KUser> list = userService.getByKey(kUser);
        System.out.print("测试getKey：" + list.toString());
    }

    @Test
    public void register() throws ParseException {
        KUser kUser = userService.getByUsername("admin");
        KUser kUser_copy = ObjectUtil.copyProperties(kUser, KUser.class);
        kUser_copy.setId(2);
        kUser_copy.setUsername("admin1");
        kUser_copy.setPassword("123456");
        kUser_copy.setBirthday("1997-01-21");
        SimpleDateFormat bartDateFormat = new SimpleDateFormat
                ("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        userService.register(kUser_copy);

    }

    @Test
    public void insert() throws ParseException {
        KUser kUser = userService.getByUsername("admin");
        KUser kUser_copy = ObjectUtil.copyProperties(kUser, KUser.class);
        kUser_copy.setId(null);
        kUser_copy.setUsername("admin1");
        kUser_copy.setPassword("123456");
        kUser_copy.setBirthday("1997-01-21");
        kUser_copy.setCreateTime(new Date());
        SimpleDateFormat bartDateFormat = new SimpleDateFormat
                ("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        baseService.insert(kUser_copy);

    }

    */
/**
     * 以ID删除
     *//*

    @Test
    public void deleteById() {
        baseService.deleteById(7);
    }

    */
/**
     * 带条件的删除
     *//*

    @Test
    public void delete() {
        KUser kUser = new KUser();
        kUser.setUsername("admin1");
        baseService.delete(kUser);
    }

    @Test
    public void select_Type() {
        KUser kUser=new KUser();
        kUser.setUsername("admin");
        System.out.print(baseService.selectList(kUser));
    }

    @Test
    public void check_email(){
        System.out.print(userService.checkEmail("zjjhwanghui@163.com"));
    }

    @Test
    public void check_mobilePhone(){
        System.out.print(userService.checkMobilePhone("18042261719"));
    }
}*/

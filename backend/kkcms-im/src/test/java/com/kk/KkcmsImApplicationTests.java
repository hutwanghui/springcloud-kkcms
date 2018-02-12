package com.kk;

import com.kk.common.util.DateUtil;
import com.kk.entity.Message;
import com.kk.entity.testtable;
import com.kk.service.IMessageService;
import com.kk.service.ITesttableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsImApplicationTests {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private ITesttableService testtableService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAdd() {
        Message message = new Message();
        message.setText("xxxxxxxxxxx");
        message.setAuthor("kuangkuang");
        message.setSendtime(new Date());
        message.setThread("kuangkuangandwanghui");
        messageService.testAdd(message);
    }

    @Test
    public void testMapperAdd() {
        Message message = new Message();
        message.setText("xxxxxxxxxxx");
        message.setAuthor("kuangkuang");
        message.setSendtime(new Date());
        message.setThread("kuangkuangandwanghui");
        messageService.insert(message);
    }

    @Test
    public void selectTest() {
        Message message = messageService.selectById(2);
        System.out.print("查询结果为：" + message.toString());
    }

    @Test
    public void selectAllTest() {
        List<Message> list = messageService.selectListAll();
        System.out.print("&&&&&&&&" + list.size());
    }

    @Test
    public void updateTest() {
        Message message = new Message();
        message.setId(3);
        message.setText("yyyyyyyyyyy");
        message.setAuthor("jiaming");
        message.setSendtime(new Date());
        message.setThread("jiamingjiaming");
        messageService.updateById(message);
    }

    @Test
    public void deleteTest() {
        Message message = new Message();
        message.setId(2);
        messageService.deleteById(4);
        messageService.delete(message);
    }
    @Test
    public void addTestTable(){
        testtable test=new testtable();
        test.setId(2);
        test.setCreatetime(new Date());
        test.setName("kuangkuang");
        testtableService.insert(test);
    }
    @Test
    public void selectTestTable(){
        testtable testtable=testtableService.selectById(2);
        System.out.print("^^^^^^^^^^^^^^^^"+testtable.toString());
        System.out.print("\n"+testtable.getCreatetime()+"\n");
        System.out.print("\n"+ DateUtil.formatDate(testtable.getCreatetime())+"\n");
    }



}

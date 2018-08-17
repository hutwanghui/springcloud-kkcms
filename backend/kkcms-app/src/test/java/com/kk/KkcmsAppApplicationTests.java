package com.kk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsAppApplicationTests {

   /* @Autowired
    private MessageRespository messageRespository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testInsertToMongoDB() {
        User user = new User("wanghui1", "assets/images/avatars/female-avatar-3.png");
        Thread thread = new Thread("twanghui1", "wanghui", "assets/images/avatars/female-avatar-3.png");
        Message message = new Message("mwanghui4", false, "测试文字44", new Date());
        message.setThread(thread);
        message.setAuthor(user);
        messageRespository.insert(message);
        threadRepository.insert(thread);
        userRepository.insert(user);
    }

    @Test
    public void testQueryToMongoDB() {
        User user = userRepository.findOne("wanghui");
        Thread thread = threadRepository.findOne("twanghui");
        Message message = messageRespository.findOne("mwanghui");
        System.out.println("使用MongoDB查询数据" + user.toString() + ":\n" + thread.toString() + ":\n" + message.toString());
    }
    @Test
    public void testQueryAll(){

    }

    @Test
    public void testSendMessage() {
        User user = new User("wanghui1", "assets/images/avatars/female-avatar-1.png");
        Thread thread = new Thread("twanghui1", "wanghui1", "assets/images/avatars/female-avatar-1.png");
        Message message = new Message("mwanghui5", false, "测试文字454", new Date());
        message.setThread(thread);
        message.setAuthor(user);
        messageRespository.insert(message);
        //threadRepository.insert(thread);
        //userRepository.insert(user);
    }

*/

}

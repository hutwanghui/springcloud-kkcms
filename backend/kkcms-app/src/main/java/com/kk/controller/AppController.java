package com.kk.controller;

import com.kk.dao.MessageRespository;
import com.kk.dao.ThreadRepository;
import com.kk.entity.Message;
import com.kk.entity.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by msi- on 2018/2/8.
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private MessageRespository messageRespository;
    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping("/getmessages")
    public List<Message> getmessages() {
        System.out.println("=============获取数据============");
        List<Message> list = messageRespository.findAll();
        System.out.println("=============数据成功============" + list.toString());
        return list;
    }

    @GetMapping("/getthreads")
    public List<Thread> getthreads() {
        System.out.println("=============获取数据============");
        List<Thread> list = threadRepository.findAll();
        System.out.println("=============数据成功============" + list.toString());
        return list;
    }

    @PostMapping("/insertmessage")
    public void insertmessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {


    }


}

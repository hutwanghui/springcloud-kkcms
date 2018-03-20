package com.kk.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Dates;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by msi- on 2018/3/3.
 */
@Component
public class MessageTask {
    public final static long ONE_Minute = 60 * 1000;

    @Scheduled(cron = "0 30-35 14 * * ?")
    public void printfTask() {
        System.out.println("=========定时任务啊啊啊啊啊=========");
    }

    /**
     * 当任务执行完毕后1分钟在执行
     */
    @Scheduled(fixedDelay = ONE_Minute)
    public void fixedDelayJob() {
        System.out.println(new Date() + " >>fixedDelay执行....");
    }

    /**
     * fixedRate就是每多次分钟一次，不论你业务执行花费了多少时间。我都是1分钟执行1次
     */
    @Scheduled(fixedRate = ONE_Minute)
    public void fixedRateJob() {
        System.out.println(new Date() + " >>fixedRate执行....");
    }
}

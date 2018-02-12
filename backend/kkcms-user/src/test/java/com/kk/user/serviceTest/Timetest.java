package com.kk.user.serviceTest;

import com.kk.common.util.DateUtil;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by msi- on 2018/1/18.
 */
@RunWith(JUnit4.class)
public class Timetest {
    @Test
    public void Time(){
        System.out.print(new Date()+"\n");
        DateTime dateTime=new DateTime(new Date());
        System.out.print(dateTime.toDate());
        SimpleDateFormat bartDateFormat = new SimpleDateFormat
                ("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(bartDateFormat.format(date));
        System.out.print(new Date().getTime());
    }
}

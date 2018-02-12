package com.kk.common.testUtil;

import com.kk.common.util.LogUtil;
import com.kk.common.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by msi- on 2018/1/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StrUriltest {
    @Test
    public void testStrTo(){
        String str_1="    XxxxxTTTTT";
        String str_2="    123   ";
        String str_3="    Xxxxx   ";
        String str_4="    Xxxxx   ";
        String str_result="";
        LogUtil.debug("去掉T:"+StrUtil.trim(str_1,"T"));
        LogUtil.debug("去掉  :"+StrUtil.trim(str_1," "));
    }
}

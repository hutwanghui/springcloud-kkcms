package com.kk.api;

/**
 * Created by msi- on 2018/2/25.
 */

import com.kk.api.entity.TabErrorInfo;
import com.kk.common.util.ExcelUtil;
import com.kk.common.util.FileUtility;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZZDSTest {
    @Test
    public void ExcelTEST() throws IOException {
        String baseUrl = "F:\\IDEAworkspace\\kkcms\\backend\\kkcms-api\\src\\main\\resources\\excelTemplate";
        File tpl = new File(baseUrl + "\\NsrErrorInfoList.xls");
        File dest = FileUtility.getUniqueFile(baseUrl, ".xls");
        //EXCEL 填充值
        Map<String, Object> tMap = new HashMap<String, Object>();
        List<TabErrorInfo> t_list = new ArrayList<>();
        TabErrorInfo tabErrorInfo = new TabErrorInfo("xxx", "xxx", "xxx", "xxx", new Date(), "xxx", "xxx", "xxx", "xxx");
        TabErrorInfo tabErrorInfo1 = new TabErrorInfo("xxx", "xxx", "xxx", "xxx", new Date(), "xxx", "xxx", "xxx", "xxx");
        t_list.add(tabErrorInfo);
        t_list.add(tabErrorInfo1);

        List<Map<String, Object>> maplist = ListbeanToMap(t_list);
        tMap.put("s1", maplist);
        ExcelUtil.excelGenerationByTemplate(tpl, dest, tMap);

        /*String name = new String("纳税人数据质量信息错误表.xls".getBytes("UTF-8"), "iso-8859-1");
        FileInputStream fin = new FileInputStream(dest);
        //FileOutputStream output = new FilterOutputStream(name);
       *//* byte[] buf = new byte[1024];
        int r = 0;
        while ((r = fin.read(buf, 0, buf.length)) != -1) {
            output.write(buf, 0, r);
        }*//*
        fin.close();
        //output.close();*/
    }

    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static List<Map<String, Object>> ListbeanToMap(List<?> obj) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object o : obj) {
            map = beanToMap(o);
            list.add(map);
        }
        return list;
    }


    @Test
    public void testMsg() {
        String context = "XXXXXXXXX@株洲睿智科技";
        if (StringUtils.contains(context, "@")) {
            String[] strings = StringUtils.split(context, "@");
            for(String str:strings){
                System.out.println("\n结果："+str);
            }
            System.out.println("分离结果为：" + strings.length + "1:" );
        }
    }

}

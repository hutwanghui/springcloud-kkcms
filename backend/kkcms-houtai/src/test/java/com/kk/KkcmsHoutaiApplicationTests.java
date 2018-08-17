package com.kk;

import com.kk.common.utils.PageUtils;
import com.kk.modules.article.entity.JjArticle;
import com.kk.modules.article.service.IJjArticleService;
import com.kk.modules.attendence.feign.IAtdRuleFeign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KkcmsHoutaiApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(KkcmsHoutaiApplicationTests.class);
    @Autowired
    private IJjArticleService articleService;

    @Test
    public void contextLoads() {
        logger.info("====123123123");
    }

    @Test
    public void testCrud_Jj() {
        Map<String, Object> params = new HashMap<>();
        params.put("tag", "java");
        List<JjArticle> list = (List<JjArticle>) articleService.queryPage(params).getList();
        logger.info("====" + list.toString());

    }

    @Autowired
    private IAtdRuleFeign iAtdRuleFeign;

    @Test
    public void testGetAtdValue() {
        Map<String, Object> params = new HashMap<>();
        params.put("attendanceRuleId", "1");
        PageUtils pageUtils = iAtdRuleFeign.queryPage_query(params);
        System.out.println("测试Fiegn" + pageUtils.toString());
    }

}

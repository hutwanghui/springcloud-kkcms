package com.kk.modules.article.controller;

import com.kk.common.annotation.SysLog;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;
import com.kk.modules.article.service.IBlArticleService;
import com.kk.modules.article.service.IJjArticleService;
import com.kk.modules.article.service.ILgArticleService;
import com.kk.modules.sys.entity.SysMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/4/21.
 */
@RestController
@RequestMapping("/sys/article")
public class ArticleController {
    @Autowired
    private IBlArticleService blservice;
    @Autowired
    private ILgArticleService lgservice;
    @Autowired
    private IJjArticleService jjservice;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = null;
        if (params.get("articleType").equals("jj")) {
            page = jjservice.queryPage(params);
        } else if (params.get("articleType").equals("lg")) {
            page = lgservice.queryPage(params);
        } else if (params.get("articleType").equals("bl")) {
            page = blservice.queryPage(params);
        }
        return R.ok().put("page", page);
    }

    /**
     * 删除
     */
    @SysLog("删除文章")
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] artIds) {
        jjservice.deleteBatchIds(Arrays.asList(artIds));
        return R.ok();
    }
}

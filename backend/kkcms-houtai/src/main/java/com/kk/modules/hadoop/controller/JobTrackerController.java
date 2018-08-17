package com.kk.modules.hadoop.controller;

import com.alibaba.fastjson.JSONObject;
import com.kk.common.annotation.SysLog;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;
import com.kk.modules.hadoop.entity.ShellTask;
import com.kk.modules.hadoop.service.IShellTaskService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by msi- on 2018/4/28.
 */
@RestController
@RequestMapping("/hadoop")
public class JobTrackerController {

    private static Logger logger = LoggerFactory.getLogger(JobTrackerController.class);

    @Autowired
    private IShellTaskService shellTaskService;

    private boolean preOsCheck() {
        String osname = System.getProperty("os.name");
        if ((osname != null) && (osname.toLowerCase().startsWith("win"))) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping("/testpath")
    public String testPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }

    @RequestMapping("/startShell")
    public R startShell(HttpServletRequest request, String shellName, String shellPath) {
        JSONObject result = new JSONObject();

        if (!preOsCheck()) {
            return R.error(0, "当前服务器操作系统不是linux");
        }
        logger.info("接收到参数:shellName=" + shellName);
        if (StringUtils.isBlank(shellName)) {
            return R.error(0, "shellName不能为空");
        }
        //脚本路径
        String cmd = shellPath + shellName;
        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", cmd);
        builder.directory(new File(shellPath));
        int runningStatus = 0;
        String s = null;
        StringBuffer sb = new StringBuffer();
        try {
            Process p = builder.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                logger.info("shell log info ...." + s);
                sb.append(s);
            }
            while ((s = stdError.readLine()) != null) {
                logger.error("shell log error...." + s);
                sb.append(s);
            }
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
                runningStatus = 1;
                logger.error("等待shell脚本执行状态时，报错...", e);
                sb.append(e.getMessage());
            }

            closeStream(stdInput);
            closeStream(stdError);

        } catch (Exception e) {
            logger.error("执行shell脚本出错...", e);
            sb.append(e.getMessage());
            runningStatus = 1;
        }
        logger.info("runningStatus = " + runningStatus);
        if (runningStatus == 0) {
            //成功
            return R.ok();
        } else {
            return R.error(0, "调用shell脚本失败");
        }
    }

    @RequestMapping("/uploadShell")
    public R uploadShell(@RequestParam("file") CommonsMultipartFile file,
                         HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String uploadPath = request.getServletContext().getRealPath("/");
        File localFile = new File(uploadPath, file.getOriginalFilename());
        try {
            file.transferTo(localFile);
            result.put("code", "1");
            result.put("msg", "脚本上传成功");
            ShellTask shellTask = new ShellTask();
            shellTask.setShellName(file.getOriginalFilename());
            shellTask.setShellPath(uploadPath);
            shellTask.setShellDesc("");
            shellTask.setUpdateTime(new Date());
            shellTaskService.insert(shellTask);
            return R.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(0, "脚本上传失败" + e.toString());
        }
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = null;
        page = shellTaskService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 删除
     */
    @SysLog("删除task")
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] artIds) {
        //TODO 同时要删除文件，待做
        shellTaskService.deleteBatchIds(Arrays.asList(artIds));
        return R.ok();
    }

    private void closeStream(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            reader = null;
        }
    }
}

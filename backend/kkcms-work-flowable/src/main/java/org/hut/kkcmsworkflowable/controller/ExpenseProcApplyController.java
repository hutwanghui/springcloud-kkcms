package org.hut.kkcmsworkflowable.controller;

import com.alibaba.fastjson.JSON;
import com.kk.common.util.http.R;
import lombok.extern.java.Log;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.cmmn.engine.impl.process.ProcessInstanceService;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hutwanghui on 2018/11/19.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
@RequestMapping("/api/proc/expense")
@Log
public class ExpenseProcApplyController {

    /**
     * 流程引擎服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 任务调度服务
     */
    @Autowired
    private TaskService taskService;
    /**
     *
     */
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 以下为纯业务代码，实现proc-报销流程的业务逻辑代码
     */

    /**
     * 通过接收用户的一个请求传入用户的ID和金额以及描述信息来开启一个报销流程，
     * 并返回给用户这个流程的Id
     *
     * @param userId
     * @param money
     * @param descption
     * @return
     */
    @RequestMapping("add")
    public R addExpense(String userId, Integer money, String descption) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        // 构建流程运行参数，启动流程，这个Key需要和xxxxProcess.bpmn20.xml中的xxxx对应
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("Expense", map);
        log.info("提交成功，流程ID为：" + processInstance.getId());
        return R.ok("申请提交成功：" + processInstance.getId());
    }


    /**
     * 获取审批管理列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("list")
    public R list(String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();

        log.info("用户:" + userId + "具有" + tasks.size() + "条待办事项需要处理。");
        tasks.stream().forEach(t -> System.out.println("任务详情：" + t.toString()));
        return R.ok()
                .put("userId", userId)
                .put("taskList", tasks.toString())
                ;
    }

    /**
     * 通过审批
     *
     * @param taskId
     * @return
     */
    @RequestMapping("apply")
    public R apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程：" + taskId + "不存在！请联系管理员查看");
        }
        //审批通过
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(task.getId(), map);
        return R.ok("通过审批");
    }

    /**
     * 审批拒绝
     *
     * @param taskId
     * @return
     */
    @RequestMapping("reject")
    public R reject(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程：" + taskId + "不存在！请联系管理员查看");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(task.getId(), map);
        return R.ok("驳回");
    }


    @RequestMapping("processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws IOException {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();
        //流程已走完的不显示
        if (processInstance == null) {
            return;
        }
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator
                .generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}


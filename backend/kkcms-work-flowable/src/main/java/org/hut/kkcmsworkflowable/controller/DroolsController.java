package org.hut.kkcmsworkflowable.controller;

import com.kk.common.util.http.R;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.hut.kkcmsworkflowable.entity.ApplyInfo;
import org.hut.kkcmsworkflowable.entity.ApplyInfoCheckResult;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by hutwanghui on 2018/12/9.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
@RestController
@RequestMapping("/api/drools")
public class DroolsController {
    @Resource
    private KieSession kieSession;

    @RequestMapping("/info")
    public R inputStudentInfo(@RequestBody ApplyInfo applyInfo) {
        ApplyInfoCheckResult result = ApplyInfoCheckResult.builder().build();
        HashMap<String, Object> hashMap = new HashMap();
        System.out.println(applyInfo);
        kieSession.insert(result);
        FactHandle f = kieSession.insert(applyInfo);
        //执行规则
        int ruleCount = kieSession.fireAllRules();
        System.out.println("本次验证学生信息共触发：" + ruleCount + "条规则");
        if (result.isPostCodeResult()) {
            System.out.println("校验合格！");
            hashMap.put(applyInfo.getName(), applyInfo);
            return R.ok(hashMap);
        } else {
            return R.error("校验失败！为满足毕业生条件");
        }
    }
}

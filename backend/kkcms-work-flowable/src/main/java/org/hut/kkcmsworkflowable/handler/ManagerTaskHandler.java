package org.hut.kkcmsworkflowable.handler;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * Created by hutwanghui on 2018/11/18.
 * email:zjjhwanhui@163.com
 * qq:472860892
 */
public class ManagerTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("经理");
    }
}

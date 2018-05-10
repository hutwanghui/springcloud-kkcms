package com.kk.api.service;

import com.kk.api.entity.UserAction;
import com.kk.api.entity.useraction.echarts;
import com.kk.api.entity.useraction.echarts_mouth;
import com.kk.common.service.BaseService;

import java.util.List;

/**
 * Created by msi- on 2018/4/29.
 */
public interface IUserActionService extends BaseService<UserAction> {
    List<echarts> selectTop10();

    List<echarts> selectBehavior();

    List<echarts> selectBehaviorChina(String type);

    echarts_mouth selectBehaviorMouth(String type);

    echarts_mouth selectBehaviorUpMouth(String type);

    echarts_mouth selectBehaviorDownMouth(String type);
}

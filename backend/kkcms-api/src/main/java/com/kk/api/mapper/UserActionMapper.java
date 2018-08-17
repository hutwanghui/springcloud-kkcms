package com.kk.api.mapper;

import com.kk.api.entity.UserAction;
import com.kk.api.entity.useraction.echarts;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserActionMapper extends Mapper<UserAction> {
    List<echarts> selectTop10();

    List<echarts> selectBehavior();

    List<echarts> selectBehaviorChina_1();

    List<echarts> selectBehaviorChina_2();

    List<echarts> selectBehaviorChina_3();

    List<echarts> selectBehaviorChina_4();

    List<Integer> selectPurchasMouth();

    List<Integer> selectBrowseMouth();

    List<Integer> selectAddcartMouth();

    List<Integer> selectCollectMouth();
}
package com.kk.api.service.impl;

import com.kk.api.entity.UserAction;
import com.kk.api.entity.useraction.echarts;
import com.kk.api.entity.useraction.echarts_mouth;
import com.kk.api.mapper.UserActionMapper;
import com.kk.api.service.IUserActionService;
import com.kk.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi- on 2018/4/29.
 */
@Service
public class UserActionImpl extends BaseServiceImpl<UserActionMapper, UserAction> implements IUserActionService {
    @Override
    public List<echarts> selectTop10() {
        return mapper.selectTop10();
    }

    @Override
    public List<echarts> selectBehavior() {
        List<echarts> echart = mapper.selectBehavior();
        echart.stream().forEach(ec -> {
            if (ec.getName().equals("1")) {
                ec.setName("浏览");
            } else if (ec.getName().equals("2")) {
                ec.setName("收藏");
            } else if (ec.getName().equals("3")) {
                ec.setName("添加购物车");
            } else {
                ec.setName("购买");
            }
        });
        return echart;
    }

    @Override
    public List<echarts> selectBehaviorChina(String type) {
        if (type.equals("1")) {
            return mapper.selectBehaviorChina_1();
        } else if (type.equals("2")) {
            return mapper.selectBehaviorChina_2();
        } else if (type.equals("3")) {
            return mapper.selectBehaviorChina_3();
        } else {
            return mapper.selectBehaviorChina_4();
        }
    }

    @Override
    public echarts_mouth selectBehaviorMouth(String type) {
        echarts_mouth em = new echarts_mouth();
        if (type.equals("1")) {
            em.setMouth_result(mapper.selectBrowseMouth());
            return em;
        } else if (type.equals("2")) {
            em.setMouth_result(mapper.selectCollectMouth());
            return em;
        } else if (type.equals("3")) {
            em.setMouth_result(mapper.selectAddcartMouth());
            return em;
        } else {
            em.setMouth_result(mapper.selectPurchasMouth());
            return em;
        }
    }

    @Override
    public echarts_mouth selectBehaviorUpMouth(String type) {
        echarts_mouth em = selectBehaviorMouth(type);
        List<Integer> echarts = new ArrayList<>();
        //TODO 待使用java8改进
        for (int i = 1; i < em.getMouth_result().size(); i++) {
            if (em.getMouth_result().get(i) > em.getMouth_result().get(i - 1)) {
                echarts.add(i);
            }
        }
        em.setMouth_result(echarts);
        return em;
    }

    @Override
    public echarts_mouth selectBehaviorDownMouth(String type) {
        echarts_mouth em = selectBehaviorMouth(type);
        List<Integer> echarts = new ArrayList<>();
        //TODO 待使用java8改进
        for (int i = 1; i < em.getMouth_result().size(); i++) {
            if (em.getMouth_result().get(i) < em.getMouth_result().get(i - 1)) {
                echarts.add(i);
            }
        }
        em.setMouth_result(echarts);
        return em;
    }
}

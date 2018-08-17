package com.kk.modules.attendence.feign.hystrix;

import com.kk.common.utils.PageUtils;
import com.kk.modules.attendence.entity.Atd_rule;
import com.kk.modules.attendence.feign.IAtdRuleFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@Component
public class IAtdRuleFeignHystrix implements IAtdRuleFeign {

    private static Logger logger = LoggerFactory.getLogger(IAtdRuleFeignHystrix.class);

    @Override
    public PageUtils queryPage_query(Map<String, Object> params) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return null;
    }

    @Override
    public Atd_rule queryInfo(Integer id) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return null;
    }

    @Override
    public boolean save_rule(Atd_rule atd_rule) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return false;
    }

    @Override
    public boolean delete_rule(Integer[] rule_ids) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return false;
    }

    @Override
    public boolean update_rule(Atd_rule atd_rule) {
        logger.info(getClass().getName() + "熔断器触发降级处理");
        return false;
    }
}

package com.kk.modules.attendence.feign;

import com.kk.common.utils.PageUtils;
import com.kk.config.FullLogFeignConfig;
import com.kk.modules.attendence.entity.Atd_rule;
import com.kk.modules.attendence.feign.hystrix.IAtdComplatedFeignHystrix;
import com.kk.modules.attendence.feign.hystrix.IAtdRuleFeignHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by msi- on 2018/5/10.
 */
@FeignClient(name = "kkcms-wx", fallback = IAtdRuleFeignHystrix.class, configuration = FullLogFeignConfig.class)
public interface IAtdRuleFeign {
    @RequestMapping(value = "/attendFegin/atd/rule/query", method = RequestMethod.GET)
    public PageUtils queryPage_query(@RequestParam Map<String, Object> params);

    @RequestMapping(value = "/attendFegin/atd/rule/info/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Atd_rule queryInfo(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "/attendFegin/atd/rule/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean save_rule(@RequestBody Atd_rule atd_rule);

    @RequestMapping(value = "/attendFegin/atd/rule/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete_rule(@RequestBody Integer[] rule_ids);

    @RequestMapping(value = "/attendFegin/atd/rule/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean update_rule(@RequestBody Atd_rule atd_rule);


}

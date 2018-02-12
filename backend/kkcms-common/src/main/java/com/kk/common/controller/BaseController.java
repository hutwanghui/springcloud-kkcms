package com.kk.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.common.constant.CommonConstant;
import com.kk.common.service.BaseService;
import com.kk.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/1/18.
 * 控制层的通用接口，含有添加、ID查询、更新、删除、返回list、分页返回、map返回
 */
public class BaseController<BaseServiceImpl extends BaseService,Entity,T> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected BaseServiceImpl baseServiceImpl;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@RequestBody Entity entity){
        int result= baseServiceImpl.insertSelective(entity);
        if(result==0){
            return JsonUtil.getFailJsonObject();
        }else{
            return JsonUtil.getSuccessJsonObject();
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject get(@PathVariable T id){
        return JsonUtil.getSuccessJsonObject(baseServiceImpl.selectById(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject update(@RequestBody Entity entity){
        int result= baseServiceImpl.updateById(entity);
        if(result==0){
            return JsonUtil.getFailJsonObject();
        }else{
            return JsonUtil.getSuccessJsonObject();
        }
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject remove(@PathVariable T id){
        int result= baseServiceImpl.deleteById(id);
        if(result <= 0){
            return JsonUtil.getFailJsonObject();
        }else{
            return JsonUtil.getSuccessJsonObject();
        }
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(){
        return JsonUtil.getSuccessJsonObject(baseServiceImpl.selectListAll());
    }

    @RequestMapping(value = "/listByPage",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listByPage(Integer pageNum, Integer pageSize){

        pageNum = pageNum == null? CommonConstant.PAGE_NUM:pageNum;
        pageSize = pageSize == null?CommonConstant.PAGE_SIZE:pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<Entity> list = baseServiceImpl.selectListAll();
        PageInfo page = new PageInfo(list);
        return JsonUtil.getSuccessJsonObject(page);
    }
    /**
     * 获取所有请求参数，封装为map对象
     * @return
     */
    public Map<String,Object> getParameterMap(){
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            parameterMap.put(key,value);
        }
        return parameterMap;
    }
}

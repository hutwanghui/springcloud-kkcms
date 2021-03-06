package com.kk.gate.validate.impl;

import com.kk.common.util.ObjectUtil;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.vo.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by msi- on 2018/2/4.
 */
@Component
public class ValidateCodeRepository {

    String OAUTH_WEB_KEY_PREFIX = "OAUTH_WEB_KEY_FOR_CODE_";

    @Qualifier("redisTemplate")
    @Resource
    private RedisTemplate<Object,Object> redis;


    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        if (code instanceof ImageCode) {
            ValidateCode validateCode_temp = ObjectUtil.copyProperties(code, ValidateCode.class);
            //设置多少时间后失效
            redis.opsForValue().set(buildKey(request, type), validateCode_temp.getCode(), 30, TimeUnit.MINUTES);
        } else {
            redis.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
        }

    }

    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redis.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redis.delete(buildKey(request, type));
    }

    private String buildKey(ServletWebRequest request, ValidateCodeType type) {

        return "code:" + type.toString().toLowerCase();
    }
}

package com.kk.gate.validate.impl;

import com.kk.common.util.ObjectUtil;
import com.kk.gate.exception.ValidateCodeException;
import com.kk.gate.validate.ValidateCodeType;
import com.kk.gate.validate.code.image.entity.ImageCode;
import com.kk.gate.validate.vo.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ValidateOauthCodeRepository {

    private static Logger logger = LoggerFactory.getLogger(ValidateOauthCodeRepository.class);

    String OAUTH_KEY_PREFIX = "OAUTH_KEY_FOR_CODE_";

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<Object, Object> redis;


    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        logger.info(">>>>>>>>>>>>>>>将验证码save进redis中>>>>>>>>>>>>>>");
        if (code instanceof ImageCode) {
            ValidateCode validateCode_temp = ObjectUtil.copyProperties(code, ValidateCode.class);
            //设置多少时间后失效
            redis.opsForValue().set(buildKey(request, type), validateCode_temp.getCode(), 30, TimeUnit.MINUTES);
        } else {
            redis.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
        }
        logger.info("<<<<<<<<<<<<<<<将验证码save进redis中<<<<<<<<<<<<<<<");
    }

    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        logger.info(">>>>>>>>>>>>>>>将验证码从redis中取出>>>>>>>>>>>>>>");
        Object value = redis.opsForValue().get(buildKey(request, type));
        logger.info("value=" + value.toString());
        if (value == null) {
            return null;
        }
        logger.info("<<<<<<<<<<<<<<<将验证码从redis中取出<<<<<<<<<<<<<<<");
        logger.info("注意：300秒后过期");
        return new ValidateCode(value.toString(),300);
    }

    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redis.delete(buildKey(request, type));
    }

    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        //设备ID就是deviceID
        String deviceId = request.getHeader("devicedId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("109");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}

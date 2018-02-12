package com.kk.common.enumtype;

/**
 * Created by msi- on 2018/1/18.
 * 枚举类
 */
public enum ResultCodeEnum {
    SUCCESS("success", 200),
    FAIL("fail", 1),
    EMAILCHECKFAIL("邮箱验证失败", 100),
    USER_EXIST("用户已存在", 101),
    MOBILE_PHONE_CHECK_FAIL("手机号码已注册", 102),
    EMAIL_EXIST("邮箱已注册", 103),
    CODE_ERROR("验证码错误", 104),
    CODE_EMPTY("验证码为空", 105),
    CODE_NOTFOUND("验证码不存在", 106),
    CODE_OUTTIME("验证码过期", 107),
    CODE_NOMATCH("验证码不匹配", 108),
    NO_DEVICEID("请求中未包含设备ID", 109),
    USER_USERNAMENULL("用户名为空", 110),
    USER_USERNAMENOTFOUND("用户名不存在", 111),
    USER_MOBILENULL("手机号为空", 112),
    USER_MOBILENOTFOUND("手机号未注册", 113),
    USER_SOCIALNULL("社交登陆用户ID为空", 114),
    USER_SOCIALNOTFOUND("社交登陆账号不存在", 115),
    NO_LOGIN("未登录", 407),
    NOT_FOUND("未找到", 404),
    TIME_OUT("超时", 408),
    CONFLICT("重复", 409),
    CONDITION_ERROR("条件错误", 412),
    UNSUPPORIED_TYPE("类型错误", 415),
    OUT_OF_RANGE("超出范围", 416),
    NULL_POINTER("对象为空", 417),
    SERVER_ERROR("服务器内部错误", 500);
    private String key;
    private Integer value;

    private ResultCodeEnum(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 根据value值 获取key值
     *
     * @param value
     * @return
     */
    public static String getString(Integer value) {
        ResultCodeEnum[] values = ResultCodeEnum.values();
        for (ResultCodeEnum alarmStatus : values) {
            if (alarmStatus.getValue() == value) {
                return alarmStatus.getKey();
            }
        }
        return null;
    }

    /**
     * 根据value值 获取key值
     *
     * @param value
     * @return
     */
    public static boolean isContainValue(Integer value) {
        ResultCodeEnum[] values = ResultCodeEnum.values();
        for (ResultCodeEnum obj : values) {
            if (obj.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}

package com.kk.gate.social.qq.connect;

import com.kk.gate.social.qq.api.QQ;
import com.kk.gate.social.qq.api.entity.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created by msi- on 2018/1/27.
 * 适配器，适配API和connect
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试api是否可用
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        //一般认为是永远可通的,
        return true;
    }

    /**
     * 适配实现部分
     * Connection是一个标准数据结构，所以要转换适配
     *
     * @param api
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues connectionValues) {
        QQUserInfo qqUserInfo = api.getUserInfo();
        //显示的用户名
        connectionValues.setDisplayName(qqUserInfo.getNickname());
        //头像。40*40
        connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        //个人主页，在QQ上没有，微博有
        connectionValues.setProfileUrl(null);
        //服务商ID
        connectionValues.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //更新个人主页状态，暂不使用
    }
}

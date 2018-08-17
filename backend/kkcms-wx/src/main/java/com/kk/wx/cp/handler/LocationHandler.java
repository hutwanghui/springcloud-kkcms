package com.kk.wx.cp.handler;


import com.kk.wx.cp.builder.TextBuilder;
import com.kk.wx.cp.utils.RedisUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class LocationHandler extends AbstractHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                                    Map<String, Object> context, WxCpService WxCpService,
                                    WxSessionManager sessionManager) {
        if (wxMessage.getMsgType().equals(WxConsts.XmlMsgType.LOCATION)) {
            //TODO 接收处理用户发送的地理位置消息
            try {
                String content = "感谢反馈，您的的地理位置已收到！";
                return new TextBuilder().build(content, wxMessage, null);
            } catch (Exception e) {
                this.logger.error("位置消息接收处理失败", e);
                return null;
            }
        }

        //上报地理位置事件
        this.logger.info("\n上报地理位置 。。。 ");
        this.logger.info("\n纬度 : " + wxMessage.getLatitude());
        this.logger.info("\n经度 : " + wxMessage.getLongitude());
        this.logger.info("\n精度 : " + String.valueOf(wxMessage.getPrecision()));

        //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用
        //TODO 进行redis缓存
        redisUtils.set(wxMessage.getFromUserName() + "_jing", String.valueOf(wxMessage.getLongitude()));
        redisUtils.set(wxMessage.getFromUserName() + "_wei", String.valueOf(wxMessage.getLatitude()));
        return null;
    }

}

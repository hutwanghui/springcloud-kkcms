package com.kk.netty.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by msi- on 2018/4/15.
 * 回调触发的处理器，负责接收并响应事件通知
 */
@Component
@Qualifier("serverHandler")
//sharable标记的作用是表示该类的实例可以被多个channel共享
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    /**
     * 每当从服务器接收到一条消息时被调用
     * 需要注意的是，由服务器发送的消息可能会被分块接收。
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        logger.info("client msg:" + s);
//        String clientIdToLong = channelHandlerContext.channel().id().asLongText();
        //  logger.info("client long id:" + clientIdToLong);
        //  String clientIdToShort = channelHandlerContext.channel().id().asShortText();
        //     logger.info("client short id:" + clientIdToShort);
        if (s.indexOf("bye") != -1) {
            //close  
            channelHandlerContext.channel().close();
        } else {
            //send to client   
            channelHandlerContext.channel().writeAndFlush("Yoru msg is:" + s);

        }
    }

    /**
     * 在到服务器的连接已经建立之后将被调用；
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        logger.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.channel().writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }

    /**
     * 在处理过程中引发异常时被调用。
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("\nChannel is disconnected");
        super.channelInactive(ctx);
    }
}

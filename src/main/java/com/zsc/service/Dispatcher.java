package com.zsc.service;


import com.zsc.transport.protocol.MessageHolder;
import com.zsc.transport.protocol.ProtocolHeader;
import com.zsc.utils.Serializer;
import io.netty.channel.Channel;
import io.netty.util.ReferenceCountUtil;
/**
 * 业务分发器.
 *
 * @author Yohann.
 */
public class Dispatcher {

    public static void dispatch(MessageHolder messageHolder) {

        if (messageHolder.getSign() != ProtocolHeader.REQUEST) {
            // 请求错误
            response(messageHolder.getChannel(), messageHolder.getSign());
            return;
        }

        switch (messageHolder.getType()) {
            // 数据
            case ProtocolHeader.LOGIN:
                break;

            // 请求错误
            default:
                response(messageHolder.getChannel(), messageHolder.getSign());
                break;
        }

        // 释放buffer
        ReferenceCountUtil.release(messageHolder);
    }

    /**
     * 请求错误响应
     *
     * @param channel
     * @param sign
     */
    private static void response(Channel channel, byte sign) {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setSign(ProtocolHeader.RESPONSE);
        messageHolder.setType(sign);
        messageHolder.setStatus(ProtocolHeader.REQUEST_ERROR);
        messageHolder.setBody("");
        channel.writeAndFlush(messageHolder);
    }
}

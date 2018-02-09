package com.zsc.transport.handler;

import com.zsc.transport.protocol.MessageHolder;
import com.zsc.transport.protocol.ProtocolHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 解码Handler.
 *
 *                                       Jelly Protocol
 *  __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __
 * |           |           |           |           |              |                          |
 *       2           1           1           1            4               Uncertainty
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 * |           |           |           |           |              |                          |
 *     Magic        Sign        Type       Status     Body Length         Body Content
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 *
 * 协议头9个字节定长
 *     Magic      // 数据包的验证位，short类型
 *     Sign       // 消息标志，请求／响应／通知，byte类型
 *     Type       // 消息类型，登录／发送消息等，byte类型
 *     Status     // 响应状态，成功／失败，byte类型
 *     BodyLength // 协议体长度，int类型
 *
 *
 * @author Yohann.
 */
public class ProtocolDecoder1 extends ByteToMessageDecoder {
    private static final Logger logger = Logger.getLogger(ProtocolDecoder1.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < ProtocolHeader.HEADER_LENGTH) {
            // 数据包长度小于协议头长度
            logger.info("数据包长度小于协议头长度");
            return;
        }
        in.markReaderIndex();

        byte[] bytes = new byte[10];
        in.readBytes(bytes);
        System.out.print(new String(bytes, "utf-8"));
    }
}

package com.zsc;

import com.zsc.service.Service;
import com.zsc.transport.netty.NettyConfig;
import com.zsc.transport.netty.NettyConfigImpl;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 启动类
 *
 * @author Yohann.
 */
public class Run {
    public static void main(String[] args) throws InterruptedException {
        start();
    }

    public static void start() throws InterruptedException {

        // 启动服务
        new Service().initAndStart();

        NettyConfig config = new NettyConfigImpl();
        config.setParentGroup(1);
        config.setChildGroup();
        config.setChannel(NioServerSocketChannel.class);
        config.setHandler();
        config.bind(20000);
    }
}

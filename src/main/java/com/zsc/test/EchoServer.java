package com.zsc.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by ZhangZaipeng on 2018/1/31 0031.
 */
public class EchoServer {
    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {

        // 创建Event Loop Group
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 Server-Bootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)  // 指定所使用的 NIO传输 Channel
                    .localAddress(new InetSocketAddress(port)) // 指定的 端口设置套 接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>(){  // 添加用户自定义Handler
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("echoHandler",new EchoServerHandler()); // 添加自定义Handler
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync(); //  异步绑定服务器 调用 sync()方法阻塞 等待直到绑定完成
            f.channel().closeFuture().sync(); // 获取Channel 的 CloseFuture 并且阻塞当前线程直到它完成
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt("8000");
        new EchoServer(port).start();
    }
}

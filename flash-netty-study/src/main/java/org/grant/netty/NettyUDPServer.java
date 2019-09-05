package org.grant.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NettyUDPServer {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(8)).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new SimpleChannelInboundHandler() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                        System.out.println(o);
                    }
                }
            ).bind(8666).sync().channel().closeFuture().sync();

    }
}

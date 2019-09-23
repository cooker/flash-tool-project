package org.grant.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * grant
 * 10/9/2019 4:51 PM
 * 描述：
 */
public class NettyLengthClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap server = new Bootstrap();
            server.group(workGroup);
            server.channel(NioSocketChannel.class)
                    .handler(new MyCInitHandler());
            ChannelFuture future = server.connect("127.0.0.1",80)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            System.out.println("连接");
                        }
                    }).sync();
            future.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("关闭");
                }
            }).sync();
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}

class MyCInitHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                        System.out.println("收到：" + ctx.channel().remoteAddress() + " >>> " + msg);
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);

                        ctx.writeAndFlush("asadas");
                    }
                });
    }
}


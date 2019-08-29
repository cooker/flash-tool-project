package org.grant.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

/**
 * grant
 * 27/8/2019 10:37 AM
 * 描述：
 */
public class NettyServer {

    public static final int port = 8666;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(8);
        NioEventLoopGroup work = new NioEventLoopGroup(16);
        try {


            ServerBootstrap server = new ServerBootstrap().group(boss, work);
            server.channel(NioServerSocketChannel.class)
                    .handler(new ChannelInitializer<ServerSocketChannel>() {
                        @Override
                        protected void initChannel(ServerSocketChannel ch) throws Exception {
                            System.out.println("服务启动中");
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(true, 5, 3, 0, TimeUnit.SECONDS))
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            try {
                                                System.out.println(msg);
                                            }finally {
                                                ReferenceCountUtil.release(msg);
                                            }
                                        }

                                        @Override
                                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                                            if (evt instanceof IdleStateEvent) {
                                                System.out.println(((IdleStateEvent) evt).state() + ">>>" + ctx.channel());
                                            }
                                            super.userEventTriggered(ctx, evt);
                                        }
                                    });
                        }

                        @Override
                        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                            System.out.println("有新连接了 ++++ ");
                            super.handlerAdded(ctx);
                        }

                    });

            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务启动成功");
                    } else {
                        System.out.println("服务启动失败");
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

}

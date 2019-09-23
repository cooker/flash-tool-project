package org.grant.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * grant
 * 27/8/2019 10:37 AM
 * 描述：
 */
public class NettyClient {

    static  NioEventLoopGroup group = new NioEventLoopGroup(8);
    static  Bootstrap bootstrap = new Bootstrap();
    static volatile Channel channel = null;
    public static void main(String[] args) throws InterruptedException {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("ping", new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                .addLast(new ObjectEncoder())
                                .addLast(new ObjectDecoder((s)->{
                                    return String.class;
                                })).addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("断线了");
                                ctx.channel().eventLoop().schedule(()->{
                                    System.out.println("断线重连");
                                    connect();
//                                    ctx.connect(new InetSocketAddress("127.0.0.1", 8666));
//                                    ctx.channel().close();
                                }, 1L, TimeUnit.SECONDS);
                                super.channelInactive(ctx);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                cause.printStackTrace();
                                ctx.close();
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if (evt instanceof IdleStateEvent){
//                                    if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE){
//                                        System.out.println("度空闲");
//                                        ctx.writeAndFlush("sasa");
//                                    }
                                }else{
                                    super.userEventTriggered(ctx, evt);
                                }
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("连接 == " + ctx);
                                channel = ctx.channel();
                                super.channelActive(ctx);
                            }
                        });
                    }
                });
        connect();

        Thread thread = new Thread(){
            @Override
            public void run() {
                int num = 100;
                while (num -- > 0){
//                    try {
//                        Thread.sleep(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    try {
                        if (channel != null && channel.isActive()){
                            channel.writeAndFlush("测试" + UUID.randomUUID().toString() + "#@");
                        }else if(channel == null || !channel.isActive()){
                            System.out.println("不可写 稍等 ++++");
                            Thread.sleep(1000L);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private static void connect(){
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8666)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture ch) throws Exception {
                        if (!ch.isSuccess()) {
                            ch.channel().close();
                            final EventLoop loop = ch.channel().eventLoop();
                            loop.schedule(new Runnable() {
                                @Override
                                public void run() {
                                    System.err.println("服务端链接不上，开始重连操作...");
                                    connect();
//                                    ch.(new InetSocketAddress("127.0.0.1", 8666));
//                                    ch.channel().close();
                                }
                            }, 1L, TimeUnit.SECONDS);
                        } else {
                            ch.channel().writeAndFlush("你好");
                            System.err.println("服务端链接成功...");
                        }
                    }
                });
    }

}

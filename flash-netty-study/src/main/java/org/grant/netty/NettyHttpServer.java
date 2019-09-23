package org.grant.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * grant
 * 10/9/2019 2:24 PM
 * 描述：
 */
public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpServerCodec())
                                .addLast(new SimpleChannelInboundHandler<HttpObject>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                                        System.out.println(msg.getClass());
                                        if (msg instanceof DefaultHttpRequest){
                                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK
                                            , Unpooled.copiedBuffer("Hello", Charset.forName("utf-8")));
                                            response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
                                            response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());

                                            ctx.writeAndFlush(response);
                                        }
                                        if (msg instanceof HttpContent){
                                            int size = ((HttpContent) msg).content().readableBytes();
                                            byte[] sa = new byte[size];
                                            ((HttpContent) msg).content().readBytes(sa);
                                            System.out.println(new String(sa));
                                        }
                                    }
                                });
                    }
                });
        ChannelFuture future = bootstrap.bind(80).sync();
        future.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}

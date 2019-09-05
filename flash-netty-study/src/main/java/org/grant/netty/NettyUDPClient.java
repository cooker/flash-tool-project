package org.grant.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static io.netty.util.CharsetUtil.UTF_8;

public class NettyUDPClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        Channel channel = bootstrap.group(new NioEventLoopGroup(8)).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new SimpleChannelInboundHandler() {
                             @Override
                             protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                 System.out.println(o);
                             }
                         }
                ).bind(8667).sync().channel();

        for (;;){
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("sa", Charset.forName("utf-8")), new InetSocketAddress("255.255.255.255", 8666))).sync();
        }

    }
}

package org.grant.rpc;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.Bzip2Decoder;
import io.netty.handler.codec.compression.Bzip2Encoder;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * grant
 * 20/9/2019 3:40 PM
 * 描述：
 */
public class RpcServerInitHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
        // 读/写 空闲
        .addLast(new IdleStateHandler(30, 60, 45, TimeUnit.MINUTES))
        .addLast(new LoggingHandler(LogLevel.INFO))
        .addLast(new ObjectDecoder(10 * 1024 * 1024, ClassResolvers.softCachingResolver(this.getClass().getClassLoader())))
        .addLast(new ObjectEncoder())
        .addLast(new RpcClientManagerHandler())
        .addLast(new BusinessServerHandler());
    }
}

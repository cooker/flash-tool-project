package org.grant.rpc;

import com.sun.tools.javac.util.Assert;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.grant.domain.rpc.RpcMessage;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * grant
 * 20/9/2019 3:38 PM
 * 描述：
 */
@Slf4j
public class RpcClient {
    EventLoopGroup workGroup = new NioEventLoopGroup(8);
    Bootstrap bootstrap = new Bootstrap();
    private String host;
    private int port;
    volatile Channel channel = null;
    volatile boolean isRun = false;

    public static void main(String[] args) throws InterruptedException {
        RpcClient rpcClient = new RpcClient();
        rpcClient.start("127.0.0.1", 8081);
        rpcClient.stop();
    }

    public void start(String host, int port){
        if (!workGroup.isShutdown()){
            isRun = true;
            this.host = host;
            this.port = port;
        }
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .handler(new RpcClientInitHanlder(this))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        this.connect();
    }

    protected void connect(){
        bootstrap.connect(host, port)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            isRun = true;
                            log.info("连接 {} - {} 成功", host, port);
                        }else {
                            if (isRun) {
                                future.channel().close();
                                log.info("正在重连 {} - {}", host, port);
                                future.channel().eventLoop().schedule(()->{
                                    RpcClient.this.connect();
                                }, 1L, TimeUnit.SECONDS);
                            }else {
                                log.info("连接关闭");
                            }
                        }
                    }
                });
    }

    public void stop() throws InterruptedException {
        log.info("正在停止 RpcClient");
        isRun = false;
        try {
            if (channel != null && channel.isActive()){
                channel.closeFuture().sync();
            }
        }finally {
            workGroup.shutdownGracefully();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean sendMsg(RpcMessage msg) {
        Assert.checkNonNull(msg, "消息不能为空！");
        Channel ch = getActiveChannel();
        if (ch != null){
            return ch.writeAndFlush(msg).isSuccess();
        }else {
            return false;
        }
    }

    private Channel getActiveChannel(){
        if (this.channel == null && isRun) {
            int tryNum = 0;
            do {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    ;
                }
            }while (this.channel == null && tryNum ++ < 5);
        }

        if (isRun){
            if (this.channel != null && this.channel.isActive()){
                return this.channel;
            }else {
                return null;
            }
        }else{
            return null;
        }
    }
}
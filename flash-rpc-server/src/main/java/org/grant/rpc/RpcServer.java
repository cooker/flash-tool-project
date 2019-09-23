package org.grant.rpc;

import com.sun.tools.javac.util.Assert;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * grant
 * 20/9/2019 3:38 PM
 * 描述：
 */
@Slf4j
public class RpcServer {

    private int port;
    private int bossThreadCnt;
    private int workThreadCnt;
    private volatile EventLoopGroup bossGroup;
    private volatile EventLoopGroup workGroup;
    private volatile Channel channel;
    private boolean isRun = false;

    public RpcServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() << 1);
    }

    public RpcServer(int port, int bossThreadCnt, int workThreadCnt) {
        this.port = port;
        this.bossThreadCnt = bossThreadCnt;
        this.workThreadCnt = workThreadCnt;
        init(bossThreadCnt, workThreadCnt);
    }

    private void init(int bossThreadCnt, int workThreadCnt){
        bossGroup = new NioEventLoopGroup(bossThreadCnt);
        workGroup = new NioEventLoopGroup(workThreadCnt);
    }

    public static void main(String[] args) throws InterruptedException {
        Assert.check(args.length != 0, "参数为空");
        RpcServer rpcServer = new RpcServer(Integer.valueOf(args[0]));
        rpcServer.start();
    }

    public void start(){
        ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new RpcServerInitHandler());

        try {
            ChannelFuture future = serverBootstrap.bind(port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("服务启动...");
                        isRun = true;
                    }else{
                        log.info("服务启动 [失败] ...");
                    }
                }
            }).sync();
            channel = future.channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("服务启动失败 ... port={}", port, e);
        }finally {
            isRun = false;
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public boolean isRun(){
        return this.isRun;
    }
}

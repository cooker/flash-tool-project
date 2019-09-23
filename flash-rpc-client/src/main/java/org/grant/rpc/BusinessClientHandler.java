package org.grant.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.grant.domain.rpc.RpcMessage;

/**
 * grant
 * 20/9/2019 5:50 PM
 * 描述：
 */
@Slf4j
public class BusinessClientHandler extends SimpleChannelInboundHandler<Object> {
    RpcClient rpcClient = null;

    public BusinessClientHandler setRpcClient(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcMessage) {

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("出现异常", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (rpcClient.isRun) {
            log.info("正在重连...");
            rpcClient.connect();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        rpcClient.channel = ctx.channel();
    }
}

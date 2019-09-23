package org.grant.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.grant.domain.rpc.RpcMessage;

/**
 * grant
 * 20/9/2019 5:44 PM
 * 描述：
 */
public class BusinessServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcMessage){
            System.out.println(((RpcMessage) msg).getRpcFlag().getRpcType());
        }else {
            ctx.fireExceptionCaught(new Exception("消息不合法"));
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}

package org.grant.domain.rpc;

import org.grant.domain.rpc.contants.RpcType;

import java.io.Serializable;
import java.util.UUID;

/**
 * grant
 * 20/9/2019 5:29 PM
 * 描述：rpc 消息体
 */
public class RpcMessage implements Serializable {
    private RpcFlag rpcFlag;
    private RpcHeader rpcHeader;
    private RpcBody rpcBody;
    public RpcMessage(RpcType rpcType, RpcHeader header, RpcBody rpcBody){
        this.rpcHeader = header;
        this.rpcBody = rpcBody;
        makeRpcFlag(rpcType, header);
    }


    private void makeRpcFlag(RpcType rpcType, RpcHeader header){
        RpcFlag rpcFlag = new RpcFlag();
        rpcFlag.rpcType(rpcType);
        rpcFlag.msgId(header.getClientId() + "-" + UUID.randomUUID().toString());
        this.rpcFlag = rpcFlag;
    }

    public RpcFlag getRpcFlag() {
        return rpcFlag;
    }

    public RpcHeader getRpcHeader() {
        return rpcHeader;
    }

    public RpcBody getRpcBody() {
        return rpcBody;
    }

}

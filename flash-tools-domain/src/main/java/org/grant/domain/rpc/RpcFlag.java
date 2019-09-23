package org.grant.domain.rpc;

import com.sun.tools.javac.util.Assert;
import org.grant.domain.rpc.contants.RpcType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * grant
 * 20/9/2019 5:36 PM
 * 描述：消息标识
 */
public final class RpcFlag implements Serializable {

    private static final long serialVersionUID = 7880542491824294272L;
    private String rpcType;
    private String msgId;
    private String sendTime;


    public RpcFlag() {
        this.sendTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
    }

    public String getSendTime(){
        return sendTime;
    }

    public String getMsgId(){
        return msgId;
    }

    public String getRpcType() {
        return rpcType;
    }

    protected void msgId(String msgId){
        Assert.checkNonNull(msgId);
        this.msgId = msgId;
    }

    protected void rpcType(RpcType type){
        this.rpcType = type.toString();
    }
}

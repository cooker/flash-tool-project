package org.grant.domain.rpc.base.body;

import org.grant.domain.rpc.RpcMessage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * grant
 * 23/9/2019 12:37 PM
 * 描述：消息回执
 */
public class Receipt implements Serializable {
    private static final long serialVersionUID = -7541720792274091006L;
    private String receiptTime;
    //原消息ID
    private String sMsgId;
    //原clientId
    private String sClientId;
    //原appId
    private String sAppId;

    public Receipt(RpcMessage message){
        this.receiptTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.sMsgId = message.getRpcFlag().getMsgId();
        this.sClientId = message.getRpcHeader().getClientId();
        this.sAppId = message.getRpcHeader().getAppId();
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public String getsMsgId() {
        return sMsgId;
    }

    public String getsClientId() {
        return sClientId;
    }

    public String getsAppId() {
        return sAppId;
    }

}

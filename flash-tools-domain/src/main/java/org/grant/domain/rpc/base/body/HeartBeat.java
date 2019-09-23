package org.grant.domain.rpc.base.body;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * grant
 * 23/9/2019 10:34 AM
 * 描述：
 */
public class HeartBeat implements Serializable {
    private static final long serialVersionUID = -3344215911020938666L;
    private String sendTime;

    public HeartBeat() {
        this.sendTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getSendTime() {
        return sendTime;
    }

}

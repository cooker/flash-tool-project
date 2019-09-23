package org.grant.domain.rpc;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * grant
 * 20/9/2019 5:25 PM
 * 描述：rcp 头属性
 */
public class RpcHeader implements Serializable {

    private static final long serialVersionUID = -5044536582910616449L;
    //客户端ID
    private String clientId;
    //应用ID clientId 里面的 appId
    private String appId;
    //访问服务的地址
    private String serviceUrl;

    private Map<String, String> others = new HashMap<String, String>();

    public RpcHeader(String clientId, String appId, String serviceUrl) {
        this.clientId = clientId;
        this.appId = appId;
        this.serviceUrl = serviceUrl;
    }

    public RpcHeader(String clientId, String appId, String serviceUrl, Map<String, String> others) {
        this(clientId, appId, serviceUrl);
        this.others = others;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAppId() {
        return appId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public Map<String, String> getOthers() {
        return Collections.unmodifiableMap(others);
    }
}

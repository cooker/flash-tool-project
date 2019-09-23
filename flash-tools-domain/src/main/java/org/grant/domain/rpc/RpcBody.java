package org.grant.domain.rpc;

import java.io.Serializable;

/**
 * grant
 * 20/9/2019 5:25 PM
 * 描述：
 */
public class RpcBody<T> implements Serializable {

    private static final long serialVersionUID = -829321598609992754L;

    private String className;

    T body;

    public RpcBody(T body) {
        this.className = body.getClass().getName();
        this.body = body;
    }

    public T getBody() throws ClassNotFoundException {
        Class.forName(className);
        return body;
    }
}

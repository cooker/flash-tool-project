package org.grant.springboot.service;

import org.grant.springboot.ContextLocal;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grant
 * 21/8/2019 2:59 PM
 * 描述：
 */
public class BaseServiceImpl implements BaseService {

    @Autowired ContextLocal contextLocal;

    protected String getReqId(){
        return contextLocal.getReqId().orElse(null);
    }

    protected String getReqIp(){
        return contextLocal.getReqIp().orElse(null);
    }
}

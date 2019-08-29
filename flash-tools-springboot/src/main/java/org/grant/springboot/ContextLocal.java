package org.grant.springboot;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * grant
 * 21/8/2019 2:45 PM
 * 描述：
 */
@Component
public class ContextLocal {
    private ThreadLocal<String> cxtReqId = new ThreadLocal<>();
    private ThreadLocal<String> ctxReqIp = new ThreadLocal<>();
    private ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public Optional<String> getReqId(){
        String reqId = cxtReqId.get();
        return Optional.ofNullable(reqId);
    }

    public void setReqId(String reqId){
        cxtReqId.set(reqId);
    }

    public Optional<String> getReqIp(){
        String reqIp = ctxReqIp.get();
        return Optional.ofNullable(reqIp);
    }

    public void setReqIp(String reqIp){
        ctxReqIp.set(reqIp);
    }


    public ThreadLocal<Map<String, Object>> getContext() {
        return context;
    }

    public void setContext(ThreadLocal<Map<String, Object>> context) {
        this.context = context;
    }
}

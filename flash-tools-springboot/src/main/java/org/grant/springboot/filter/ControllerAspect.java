package org.grant.springboot.filter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.grant.springboot.ContextLocal;
import org.grant.springboot.SpringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * grant
 * 21/8/2019 3:25 PM
 * 描述：
 */
@Aspect
@Component
public class ControllerAspect {
    @Autowired ContextLocal contextLocal;

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void req(){ }

    @Before("req()")
    public void loadReq(){
        String reqId = UUID.randomUUID().toString();
        contextLocal.setReqId(reqId);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = SpringHelper.getIpAddress(request);
        contextLocal.setReqIp(ip);
    }
}

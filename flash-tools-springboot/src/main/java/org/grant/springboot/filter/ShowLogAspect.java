package org.grant.springboot.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.grant.springboot.ContextLocal;
import org.grant.springboot.ShowLog;
import org.grant.springboot.SpringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * grant
 * 21/8/2019 3:11 PM
 * 描述：
 */
@Component
@Slf4j
@Aspect
public class ShowLogAspect {

    @Autowired ContextLocal contextLocal;
    private ThreadLocal<String> title = new ThreadLocal<>();

    @Pointcut("@annotation(org.grant.springboot.ShowLog)")
    public void showLog(){ }

    @Before("showLog()")
    public void before(JoinPoint point){
        String reqId = contextLocal.getReqId().orElse(null);
        String reqIp = contextLocal.getReqIp().orElse(null);
        HttpServletRequest request = SpringHelper.getRequest();
        String url = request.getRequestURL().toString();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ShowLog showLog = method.getAnnotation(ShowLog.class);
        String title = "";
        if (showLog != null) {
            title = showLog.value();
        }
        this.title.set(showLog.value());
        log.info("reqId={} [{}] 消息请求 ip={} method={} url={}", reqId, title, reqIp, request.getMethod(),url);
        log.info("reqId={} [{}] 调用 -- 请求方法 {}.{} >>> {}", reqId, title, point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), Arrays.toString(point.getArgs()));
    }


    @AfterReturning(returning = "ret", value = "showLog()")
    public void after(Object ret){
        String reqId = contextLocal.getReqId().orElse(null);
        log.info("reqId={} [{}] 返回请求 {}", reqId, title.get(), ret);
        title.remove();
    }
}

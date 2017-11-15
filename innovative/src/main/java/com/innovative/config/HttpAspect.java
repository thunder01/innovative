package com.innovative.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * 记录用户行为
 * @author huang
 *
 */
@Aspect
@Configuration
public class HttpAspect {
 
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);
    /**
     * 监控所有controller
     */
    @Pointcut(value = "execution(public * com.innovative.controller.*.*(..))")
    public void p(){}
    /**
     * 返回用户id、请求路径、请求方法、调用的远程方法、ip
     * @param joinPoint
     */
    @Before("p()") 
    public void doBefore(JoinPoint joinPoint){
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userid = request.getParameter("userId");

        MDC.put("userid",userid);
        MDC.put("ip",request.getRemoteAddr());
        MDC.put("url",request.getRequestURL().toString());
        MDC.put("method",request.getMethod());
        MDC.put("class_method",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"()");
    }

    @After("p()")
    public void doAfter(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        MDC.put("status",""+response.getStatus());

    }
}
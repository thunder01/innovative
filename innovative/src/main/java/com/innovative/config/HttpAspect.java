package com.innovative.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.innovative.bean.LoggerRecord;
import com.innovative.service.LoggerRecordService;
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
        HttpServletResponse response = attributes.getResponse();
        String userid = request.getParameter("userId");
        //URL
        LOGGER.warn("userid={}", userid);
        LoggerRecord loggerRecord= new LoggerRecord();
        loggerRecord.setUserid(userid);
        loggerRecord.setMethod(request.getMethod());
        loggerRecord.setUrl(request.getRequestURL().toString());
        loggerRecord.setIp(request.getRemoteAddr());
        loggerRecord.setArgs(joinPoint.getArgs().toString());
        loggerRecord.setClass_method(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"()");
        loggerRecord.setStatus(response.getStatus()+"");
        LoggerRecordService loggerRecordService = WebApplicationContextUtils.getRequiredWebApplicationContext
        		(request.getServletContext()).getBean(LoggerRecordService.class);
        loggerRecordService.addLoggerRecord(loggerRecord);
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("url", request.getRequestURL());
        map.put("method", request.getMethod());
        map.put("ip", request.getRemoteAddr());
        map.put("class.Method", joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+"()");
        LOGGER.warn("action={}", map);
    }
 
    @After("p()")
    public void doAfter(){
        LOGGER.warn("HttpAspect doAfter Running : "+new Date());
    }
 
}
package com.innovative.fileter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.innovative.bean.Logger;
import com.innovative.bean.User;
import com.innovative.dao.LoggerDao;
import com.innovative.service.LoggerService;
import com.innovative.service.UserService;
import com.innovative.utils.LoggerUtils;
import com.innovative.utils.Misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
@WebFilter(urlPatterns = "/*",filterName = "userFilter")
public class Fileter implements Filter {
    Log log = LogFactory.getLog(Fileter.class);
    @Override
    /**
     * 初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    	// 初使化时将已静态化的testService实例化
    }

    @Override
    /**
     * 过滤器内容
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        UserService service;
        LoggerService loggerService;
        Logger logger = new Logger();
        logger.setId(Misc.base58Uuid());
        String url = request.getRequestURI().substring(request.getContextPath().length());
        logger.setUri(url);
        logger.setSessionid(request.getRequestedSessionId());
        String paramdata = JSON.toJSONString(request.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        logger.setParam(paramdata);
        logger.setClientip(LoggerUtils.getIp(request));
        logger.setMethod(request.getMethod());
        
        String [] urls=url.split("/");
        if (urls[1].equals("file")||urls[1].equals("poi")||url.equals("/crossdomain.xml")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
           
            boolean isfile=false;
            HttpSession session=request.getSession();
            String userId=request.getParameter("userId");
          
            service=factory.getBean(UserService.class);
            User userben=service.getUser(userId);
            if (userben!=null) {
                session.setAttribute("userId",userben);
                isfile=true;
               
            }
            else {
                isfile =false;
                response.sendError(400,"非法用户请求");
            }
            User users= (User) session.getAttribute("userId");
            if (userben!=null&&userben.getUserId().equals(users.getUserId())&&userId!=null){
                isfile=true;
            }
            else{
                isfile =false;
            }
            if (isfile) filterChain.doFilter(servletRequest,servletResponse);
            logger.setHttpstatuscode( response.getStatus()+""); 
            loggerService = factory.getBean(LoggerService.class);
            loggerService.addLogger(logger);
        }
    }

    @Override
    /**
     * 销毁
     */
    public void destroy() {

    }
}

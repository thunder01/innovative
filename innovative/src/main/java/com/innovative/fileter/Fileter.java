package com.innovative.fileter;
import com.innovative.bean.User;
import com.innovative.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(urlPatterns = "/*",filterName = "userFilter")
public class Fileter implements Filter {
    Log log = LogFactory.getLog(Fileter.class);
    @Override
    /**
     * 初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    /**
     * 过滤器内容
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        UserService service;
        boolean isfile=false;
        HttpSession session=request.getSession();
        String userId=request.getParameter("userId");
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
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
    	//filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    /**
     * 销毁
     */
    public void destroy() {

    }
}

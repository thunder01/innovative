package com.innovative.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innovative.bean.User;
/**
 * 主要处理当前登录用户信息
 * @author cj
 *
 */
public  class CookiesUtil {


    
	/**
	 * 根据cookie名字获取值 从session中从新取用户名
	 * @param cookiename
	 * @return
	 */
	 public static  String getCookieValue(HttpServletRequest request,String cookiename){
		  String username = "admin";
		  HttpSession session=request.getSession();
		  User user = (User) session.getAttribute("userId");
		  if(user != null){
			  username =  user.getUserId();
		  }
		   
		return username;
			
	 }
	 /**
	  * 拿出所有cookies 此方法并没有遗弃，相信后期会用的
	  * @return
	  */
	 public static  Cookie[] getCookies(HttpServletRequest request){
			Cookie[] cookies = request.getCookies();
			return request.getCookies();
			
	 }

}

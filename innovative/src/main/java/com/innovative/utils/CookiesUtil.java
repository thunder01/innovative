package com.innovative.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public  class CookiesUtil {


    
	/**
	 * 根据cookie名字获取值
	 * @param cookiename
	 * @return
	 */
	 public static  String getCookieValue(HttpServletRequest request,String cookiename){
		  String username = "admin";
			Cookie[] cookies = getCookies(request);
			if (null==cookies) {//如果没有cookie数组
		        return username;
		    } else {
		        for(Cookie cookie : cookies){
		        	if( cookie.getName().equals("user_uid"))
		        		username = cookie.getValue();
		        }
		    }
			return username;
			
	 }
	 /**
	  * 拿出所有cookies
	  * @return
	  */
	 public static  Cookie[] getCookies(HttpServletRequest request){
			Cookie[] cookies = request.getCookies();
			
			return request.getCookies();
			
	 }

}

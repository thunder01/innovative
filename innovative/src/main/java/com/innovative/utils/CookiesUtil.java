package com.innovative.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

public class CookiesUtil {


    
	 @Autowired
    private static HttpServletRequest request;
    
	/**
	 * 根据cookie名字获取值
	 * @param cookiename
	 * @return
	 */
	 public static  String getCookieValue(String cookiename){
		  String username = "admin";
			Cookie[] cookies = getCookies();
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
	 public static  Cookie[] getCookies(){
			Cookie[] cookies = request.getCookies();
			
			return request.getCookies();
			
	 }

}

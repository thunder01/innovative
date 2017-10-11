package com.innovative.utils;

  
import org.springframework.web.bind.annotation.ControllerAdvice;  
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;  
  
/**
 * 
 * @author cj 解决跨越问题 
 *
 */

@ControllerAdvice(basePackages = "com.innovative.controller.UserController")  
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{  
  
	  public JsonpAdvice() {  
		  
	        super("jsonpCallback","user");  
	    }  
}  
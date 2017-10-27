package com.innovative.utils;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;



public class BaseController {

//    private static Logger log = Logger.getLogger(BaseController.class);
	private static Logger log = LoggerFactory.getLogger(BaseController.class);
    @ExceptionHandler(Throwable.class)
    public JsonResult handleException(Throwable ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new JsonResult(false, ex.getMessage());
    }
}

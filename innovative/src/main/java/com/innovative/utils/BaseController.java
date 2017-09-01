package com.innovative.utils;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;



public class BaseController {

    private static Logger log = Logger.getLogger(BaseController.class);

    @ExceptionHandler(Throwable.class)
    public JsonResult handleException(Throwable ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new JsonResult(false, ex.getMessage());
    }
}

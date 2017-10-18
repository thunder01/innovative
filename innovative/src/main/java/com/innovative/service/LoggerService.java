package com.innovative.service;


import com.innovative.bean.Logger;
import com.innovative.dao.LoggerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    @Autowired
    private LoggerDao loggerDao;
     
    
    public boolean addLogger(Logger logger) {
    	
        return loggerDao.addLogger(logger)> 0 ? true : false ;
    }

}

package com.innovative.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.innovative.bean.LoggerRecord;
import com.innovative.dao.LoggerRecordDao;

@Service
public class LoggerRecordService {
	@Resource
	private LoggerRecordDao loggerRecordDao;
	
	public int addLoggerRecord(LoggerRecord loggerRecord){
		int result = loggerRecordDao.addLoggerRecord(loggerRecord);
		return result > 0 ? result : 0;
	}
}

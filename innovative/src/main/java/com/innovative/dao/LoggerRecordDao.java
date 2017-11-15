package com.innovative.dao;

import com.innovative.bean.LoggerRecord;

public interface LoggerRecordDao {
	/**
	 * 添加一个日志
	 * @param loggerRecord
	 * @return
	 */
	public int addLoggerRecord(LoggerRecord loggerRecord);
}

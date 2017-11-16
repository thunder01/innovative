package com.innovative.dao;

import com.innovative.bean.LoggerUser;

/**
 * Created by jacks on 2017/11/16.
 */
public interface LoggerUserDao {
    /**
     * 添加日志
     * @param loggerUser
     */
    void addLog(LoggerUser loggerUser);
}

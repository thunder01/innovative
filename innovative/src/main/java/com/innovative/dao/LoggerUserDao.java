package com.innovative.dao;

import com.innovative.bean.LoggerUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jacks on 2017/11/16.
 */
public interface LoggerUserDao {
    /**
     * 添加日志
     * @param loggerUser
     */
    void addLog(LoggerUser loggerUser);

    /**
     * 查询日志
     * @param pageSize
     * @param startIndex
     * @return
     */
    List<LoggerUser> findLog(@Param("pageSize")int pageSize,@Param("startIndex")int startIndex);

    /**
     * 查总条数
     * @return
     */
    Integer totalcount();
}

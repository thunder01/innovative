package com.innovative.dao;

import com.innovative.bean.Association;
import com.innovative.bean.FileLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileLogDao {

    FileLog  queryList(@Param("fileName") String fileName);

    int addFileLog(FileLog fileLog);

    int update(FileLog fileLog);
}

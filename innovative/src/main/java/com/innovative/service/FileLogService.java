package com.innovative.service;

import com.innovative.bean.FileLog;
import com.innovative.dao.FileLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileLogService {
@Autowired
private FileLogDao dao;

    /**
     * 查询内容
     * @param fileName
     * @return
     */
    public FileLog queryList(String fileName){
        FileLog fileLog=dao.queryList(fileName);
        return  fileLog;
    }

    /**
     * 添加内容
     * @param fileLog
     * @return
     */
    public boolean addFileLog(FileLog fileLog){
        return (dao.addFileLog(fileLog)>0?true:false);
    }

    /**
     *
     * @param fileLog
     * @return
     */
    public  boolean update(FileLog fileLog){
        return (dao.update(fileLog)>0?true:false);
    }
}

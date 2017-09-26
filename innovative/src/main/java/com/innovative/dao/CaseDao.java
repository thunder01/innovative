package com.innovative.dao;

import com.innovative.bean.Case;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CaseDao {


    /**
     * 查询文件所有的内容
     *
     */
    List<Case> getFileList();
    /**
     * 根据ID查找文件的内容
     */
    List<Case> getListById(@Param("id") int id);

    /**
     * 添加logo和标题内容
     */
    int add(Case cases);

    /**
     * 修改功能
     */
    int update(Case cases);

}

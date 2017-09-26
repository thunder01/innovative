package com.innovative.service;

import com.innovative.bean.Case;
import com.innovative.dao.CaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CaseService {
    @Autowired
    private CaseDao dao;
    /**
     * 查询文件所有内容
     */
      public  List<Case> getFileList(){

          return dao.getFileList();
      }

    /**
     * 查询文件所有内容
     */
    public  List<Case> getListById(int id){

        return dao.getListById(id);
    }

    /**
     * 添加内容
     */
      public  boolean add(Case cases){
          return (dao.add(cases)>0 ?true:false);
      }

    /**
     * 修改内容
     */

       public  boolean update(Case cases){
           return (dao.update(cases)>0 ?true:false);
       }
}

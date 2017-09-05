package com.innovative.dao;

import com.innovative.bean.Association;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AssociationDao {



    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    Association getAssociation(@Param("id") Integer id);




    /**
     * 行业协会列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    List<Association> getAssociationList(@Param("sectors") String sectors, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);



    /**
     * 查询相关行业领域的记录总条数
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    int getTotalCount(@Param("sectors") String sectors);



    /**
     * 添加行业协会
     * @param association 新增参数
     * @return
     */
    int addAssociation(Association association);


    /**
     * 修改行业协会
     * @param association 修改参数
     * @return
     */
    int updateAssociation(Association association);






}

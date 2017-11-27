package com.innovative.dao;

import com.innovative.bean.Association;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociationDao {



    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    Association getAssociation(@Param("id") String id);




    /**
     * 行业协会列表页
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    List<Association> getAssociationList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("sectors") String sectors,@Param("key1") String key1,@Param("key2") String key2);




    /**
     * 查询相关行业领域的记录总条数
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    int getTotalCount(@Param("sectors")String sectors, @Param("key1")String key1, @Param("key2")String key2);



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




	boolean deleteAssociation(@Param("id")String id);





}

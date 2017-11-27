package com.innovative.dao;


import com.innovative.bean.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganizationDao {



    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    Organization getOrganization(@Param("id") String id);


    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    List<Organization> getOrganizationList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("sectors") String sectors,@Param("key1") String key1, @Param("key2")String key2);



    /**
     * 查询相关行业领域的记录总条数
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    int getTotalCount(@Param("sectors")String sectors, @Param("key1")String key1, @Param("key2")String key2);


    /**
     * 添加机构信息
     * @param organization 新增参数
     * @return
     */
    int addOrganization(Organization organization);



    /**
     * 修改机构信息
     * @param organization 修改参数
     * @return
     */
    int updateOrganization(Organization organization);

/**
 * 删除
 * @param id
 * @return
 */
	boolean deleteOrganization(String id);





}

package com.innovative.dao;


import com.innovative.bean.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrganizationDao {



    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    Organization getOrganization(@Param("id") Integer id);


    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    List<Organization> getOrganizationList(@Param("sectors") String sectors, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);



    /**
     * 查询相关行业领域的记录总条数
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    int getTotalCount(@Param("sectors") String sectors);


    /**
     * 添加机构信息
     * @param params 新增参数
     * @return
     */
    int addOrganization(Map<String, Object> params);



    /**
     * 修改机构信息
     * @param params 修改参数
     * @return
     */
    int updateOrganization(Map<String, Object> params);





}

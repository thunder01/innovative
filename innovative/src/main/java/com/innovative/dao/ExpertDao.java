package com.innovative.dao;

import com.innovative.bean.Expert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpertDao {



    /**
     * 根据id获取专家详情
     * @param id 专家id
     * @return
     */
    Expert getExpert(@Param("id") Integer id);




    /**
     * 专家列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    List<Expert> getExpertList(@Param("sectors") String sectors, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);



    /**
     * 查询相关行业领域的记录总条数
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    int getTotalCount(@Param("sectors") String sectors);



    /**
     * 添加专家信息
     * @param params 新增参数
     * @return
     */
    int addExpert(Map<String, Object> params);


    /**
     * 修改专家信息
     * @param expert 修改参数
     * @return
     */
    int updateExpert(Expert expert);




	int addForExpert(Expert expert);



}

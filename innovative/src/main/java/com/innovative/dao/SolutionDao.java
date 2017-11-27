package com.innovative.dao;

import com.innovative.bean.Solution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SolutionDao {

    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    Solution getSolutionById(@Param("id") String id);

    /**
     * 分页条件查询
     *
     * @param startIndex 开始条数
     * @param pageSize   展示条数
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    List<Solution> getSolutionListByCondition(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("sectors")String sectors, @Param("key1")String key1, @Param("key2")String key2);

    /**
     * 查询满足条件的总条数
     * @param sectors 
     * @param key2 
     * @param key1 
     *
     * @return
     */
    int getCountByCondition(@Param("sectors")String sectors, @Param("key1")String key1, @Param("key2")String key2);


    /**
     * 新增技术报告
     *
     * @param solution 参数集合
     * @return
     */
    int insertSolution(Solution solution);

    /**
     * 修改方案信息
     *
     * @param solution 参数集合
     * @return
     */
    int updateSolution(Solution solution);
    
    /**
     * 删除解决方案
     * @param id
     * @return
     */
	boolean deleteSolution(@Param("id")String id);
}

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
     * @return
     */
    List<Solution> getSolutionListByCondition(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的总条数
     *
     * @return
     */
    int getCountByCondition();

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
}

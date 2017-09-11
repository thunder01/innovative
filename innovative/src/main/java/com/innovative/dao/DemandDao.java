package com.innovative.dao;

import com.innovative.bean.Association;
import com.innovative.bean.Demand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DemandDao {

    /**
     * 根据ID查询
     */
     Demand getDemand(@Param("id") int id);


    /**
     * 添加内容
     */
    int addDemand(Demand demand);



    /**
     * 根据ID修改文件状态
     */
    int updateDemand(@Param("id") int id);


    /**
     * 列表页
     * @return
     */
    List<Demand> getDemandList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询相关行业领域的记录总条数
     * @return
     */
    int getTotalCount();
}

package com.innovative.dao;

import com.innovative.bean.TechnicalReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TechnicalReportDao {

    /**
     * 根据id获取技术报告
     *
     * @param id 技术报告id
     * @return
     */
    TechnicalReport getTechnicalReportById(@Param("id") Integer id);

    /**
     * 分页条件查询
     *
     * @param sectors   关键字
     * @param startIndex 开始条数
     * @param pageSize   展示条数
     * @return
     */
    List<TechnicalReport> getTechnicalReportListByCondition(@Param("sectors") String sectors, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的总条数
     *
     * @param sectors 关键字
     * @return
     */
    int getCountByCondition(@Param("sectors") String sectors);

    /**
     * 新增技术报告
     *
     * @param p 参数集合
     * @return
     */
    int insertTechnicalReport(Map<String, Object> p);

    /**
     * 修改技术报告信息
     *
     * @param params 参数集合
     * @return
     */
    int updateTechnicalReport(Map<String, Object> params);
}

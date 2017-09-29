package com.innovative.dao;

import com.innovative.bean.TechnicalReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TechnicalReportDao {

    /**
     * 根据id获取技术报告
     *
     * @param id 技术报告id
     * @return
     */
    TechnicalReport getTechnicalReportById(@Param("id") String id);

    /**
     * 分页条件查询
     *
     * @param startIndex 开始条数
     * @param pageSize   展示条数
     * @return
     */
    List<TechnicalReport> getTechnicalReportListByCondition(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的总条数
     *
     * @return
     */
    int getCountByCondition();

    /**
     * 新增技术报告
     *
     * @param technicalReport 参数集合
     * @return
     */
    int insertTechnicalReport(TechnicalReport technicalReport);

    /**
     * 修改技术报告信息
     *
     * @param technicalReport 参数集合
     * @return
     */
    int updateTechnicalReport(TechnicalReport technicalReport);
    /**
     * 删除技术报告
     * @param id
     * @return
     */
	boolean deleteTechnicalReport(@Param("id")String id);
}

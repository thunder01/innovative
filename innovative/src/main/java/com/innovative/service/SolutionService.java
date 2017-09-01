package com.innovative.service;


import com.innovative.bean.Solution;
import com.innovative.bean.TechnicalReport;
import com.innovative.dao.SolutionDao;
import com.innovative.dao.TechnicalReportDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;


    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    public Solution getSolutionById(Integer id) {

        return solutionDao.getSolutionById(id);

    }

    /**
     * 分页条件查询方案list
     *
     * @param sectors 关键字
     * @param pageNum  页码
     * @return
     */
    public Map<String, Object> getSolutionListByCondition(String sectors, int pageNum) {


        if (sectors != null) {
            sectors = "{" + sectors + "}";
        }

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Solution> items = solutionDao.getSolutionListByCondition(sectors, offset, limit);
        int totalCount = solutionDao.getCountByCondition(sectors);

        //数据组装
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("offset", offset);
        map.put("limit", limit);
        map.put("count", items.size());

        return map;

    }

    /**
     * 新增技术报告
     *
     * @param params 参数集合
     * @return
     */
    public boolean insertSolution(Map<String, Object> params) {

        int result = solutionDao.insertSolution(params);

        return result > 0 ? true : false;
    }

    /**
     * 修改技术报告信息
     *
     * @param params 参数集合
     * @return
     */
    public boolean updateSolution(Map<String, Object> params) {

        int result = solutionDao.updateSolution(params);

        return result > 0 ? true : false;
    }
}

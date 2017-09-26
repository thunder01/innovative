package com.innovative.service;


import com.innovative.bean.TechnicalReport;
import com.innovative.dao.FileDao;
import com.innovative.dao.TechnicalReportDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TechnicalReportService {

    @Autowired
    private TechnicalReportDao technicalReportDao;
    @Autowired
    FileDao fileDao;

    /**
     * 根据id获取技术报告
     *
     * @param id 技术报告id
     * @return
     */
    public TechnicalReport getTechnicalReportById(String id) {

    	TechnicalReport technicalReport = technicalReportDao.getTechnicalReportById(id);
		if(technicalReport!=null){
		   List<String> url = fileDao.getPhotoByMOdAndId(id, "reportPhoto");
		   if(url != null && url.size() > 0 )
			   technicalReport.setPictures(url.get(0));
		}
		return technicalReport;
    }

    /**
     * 分页条件查询技术报告list
     *
     * @param pageNum  页码
     * @return
     */
    public Map<String, Object> getTechnicalReportListByCondition(int pageNum) {

/*
        if (sectors != null) {
            sectors = "{" + sectors + "}";
        }
*/
        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<TechnicalReport> items = technicalReportDao.getTechnicalReportListByCondition(offset, limit);
        for(TechnicalReport te : items){
        	if(null == te || "".equals(te.getId()))
        		continue;
        	  List<String> url = fileDao.getPhotoByMOdAndId(te.getId(), "reportPhoto");
   		   if(url != null && url.size() > 0 )
   			   te.setPictures(url.get(0));
        }
        int totalCount = technicalReportDao.getCountByCondition();

        //数据组装
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());

        return map;

    }

    /**
     * 新增技术报告
     *
     * @param technicalReport 参数集合
     * @return
     */
    @Transactional
    public boolean insertTechnicalReport(TechnicalReport technicalReport) {

        technicalReportDao.insertTechnicalReport(technicalReport);
        //增加成功
		 return fileDao.updateFile(technicalReport.getId());
    }

    /**
     * 修改技术报告信息
     *
     * @param technicalReport 参数集合
     * @return
     */
    public boolean updateTechnicalReport(TechnicalReport technicalReport) {

        int result = technicalReportDao.updateTechnicalReport(technicalReport);
        
        fileDao.updateFile(technicalReport.getId());

        return result > 0 ;
    }
}

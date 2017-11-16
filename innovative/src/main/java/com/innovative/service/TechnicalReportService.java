package com.innovative.service;


import com.innovative.bean.Sections;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
import com.innovative.dao.FileDao;
import com.innovative.dao.SectionsDao;
import com.innovative.dao.TechnicalReportDao;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TechnicalReportService {

    @Autowired
    private TechnicalReportDao technicalReportDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private SectionsService sectionsService;
    @Autowired
    UserService userService;

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
		   User user = userService.getUser(technicalReport.getCreatedBy());
 		  if(user!=null){
  			integralService.managerIntegral(5, technicalReport.getCreatedBy(), technicalReport.getId());
 		  }
		}
		return technicalReport;
    }

    /**
     * 分页条件查询技术报告list
     *
     * @param pageNum  页码
     * @param sectors 
     * @return
     */
    public Map<String, Object> getTechnicalReportListByCondition(int pageNum, String sectors) {


        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<TechnicalReport> items = technicalReportDao.getTechnicalReportListByCondition(offset, limit,sectors);
        for(TechnicalReport te : items){
        	if(null == te || "".equals(te.getId()))
        		continue;
        	  List<String> url = fileDao.getPhotoByMOdAndId(te.getId(), "reportPhoto");
   		   if(url != null && url.size() > 0 )
   			   te.setPictures(url.get(0));
        }
        int totalCount = technicalReportDao.getCountByCondition(sectors);

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
     * 新增技术报告,同时将信息添加到科技专栏表中。
     *
     * @param technicalReport 参数集合
     * @return
     */
    @Transactional
    public boolean insertTechnicalReport(TechnicalReport technicalReport) {

        int result = technicalReportDao.insertTechnicalReport(technicalReport);
        //添加到科技专栏
        Sections sections=new Sections();
        sections.setTitle(technicalReport.getName());
        sections.setResource("内部资源/技术报告");
        sections.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sections.setSectors(technicalReport.getSectors());
        sections.setTags(technicalReport.getTags());
        sections.setResume(technicalReport.getSummary());
        sections.setCotent(technicalReport.getContent());
        sections.setCreateBy(technicalReport.getCreatedBy());
        sections.setImgid(technicalReport.getPictures());
        sections.setFirstid(technicalReport.getId());
        sections.setType("1");
        sectionsService.addSection(sections);
        //将审批状态置为通过
        String sectionId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
        sections.setState("1");
        sections.setId(sectionId);
        sectionsService.updateSection(sections);

        //增加成功
        if(result>0){
        	integralService.managerIntegral(10, technicalReport.getCreatedBy(), technicalReport.getId());
        }
		 return fileDao.updateFile(technicalReport.getId());
    }

    /**
     * 修改技术报告信息
     *
     * @param technicalReport 参数集合
     * @return
     */
    @Transactional
    public boolean updateTechnicalReport(TechnicalReport technicalReport) {

        int result = technicalReportDao.updateTechnicalReport(technicalReport);
        //删除之前的文件
        fileDao.deleteFiles(technicalReport.getId(),"reportFile");
        //新增新的文件
        fileDao.updateFile(technicalReport.getId());

        //查询技术报告修改后的内容
        TechnicalReport technicalReport1=technicalReportDao.getTechnicalReportById(technicalReport.getId());
        //查询对应的科技专栏id
        String sectionId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
        //修改科技专栏
        Sections sections=new Sections();
        sections.setId(sectionId);
        sections.setTitle(technicalReport1.getName());
        sections.setSectors(technicalReport1.getSectors());
        sections.setTags(technicalReport1.getTags());
        sections.setResume(technicalReport1.getSummary());
        sections.setCotent(technicalReport1.getContent());
        sections.setUpdateBy(technicalReport1.getUpdatedBy());
        sections.setImgid(technicalReport1.getPictures());
        sectionsService.updateSection(sections);

        return result > 0 ;
    }

	public boolean deleteTechnicalReport(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

        //删除科技专栏
        String sectionId=sectionsService.getIdByFirstId(id,"1");
        sectionsService.deleteSection(sectionId);

		return technicalReportDao.deleteTechnicalReport(id);
	}
}

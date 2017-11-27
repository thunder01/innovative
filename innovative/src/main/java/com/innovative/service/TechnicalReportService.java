package com.innovative.service;


import com.innovative.bean.LoggerUser;
import com.innovative.bean.Sections;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
import com.innovative.dao.*;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
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
    private LoggerUserDao loggerUserDao;
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
     * @param keyword 
     * @return
     */
    public Map<String, Object> getTechnicalReportListByCondition(int pageNum, String sectors, String keyword) {


        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        String key1 = "%" + keyword + "%".trim();
	     String key2 = "{" + keyword + "}".trim();
        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<TechnicalReport> items = technicalReportDao.getTechnicalReportListByCondition(offset, limit,sectors,key1,key2);
        for(TechnicalReport te : items){
        	if(null == te || "".equals(te.getId()))
        		continue;
        	  List<String> url = fileDao.getPhotoByMOdAndId(te.getId(), "reportPhoto");
   		   if(url != null && url.size() > 0 )
   			   te.setPictures(url.get(0));
        }
        int totalCount = technicalReportDao.getCountByCondition(sectors,key1,key2);

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

        //增加成功
        if(result>0){
            //添加到科技专栏
            Sections sections=addSectionByTechnicalReport(technicalReport);
            sectionsService.addSection(sections);
            //设置为审批通过
            String sectionId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
            sections.setState("1");
            sections.setId(sectionId);
            sectionsService.updateSection(sections);
            //添加积分
            integralService.managerIntegral(10, technicalReport.getCreatedBy(), technicalReport.getId());
           //添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","技术报告",technicalReport.getId(),technicalReport.getName());
            loggerUserDao.addLog(loggerUser);
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
        //修改技术报告
        int result = technicalReportDao.updateTechnicalReport(technicalReport);
        //删除之前的文件
        fileDao.deleteFiles(technicalReport.getId(),"reportFile");
        //新增新的文件
        fileDao.updateFile(technicalReport.getId());

        if (result>0){
            //查询技术报告修改后的内容
            TechnicalReport technicalReport1=technicalReportDao.getTechnicalReportById(technicalReport.getId());

            //从科技专栏表中查找信息是否存在，若存在再修改、若不存在则添加到科技专栏表
            String sectionsId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
            if (sectionsId!=null&&!("".equals(sectionsId))){
                //修改科技专栏
                Sections sections=addSectionByTechnicalReport(technicalReport1);
                sections.setId(sectionsId);
                sectionsService.updateSection(sections);
            }else {
                Sections sections=addSectionByTechnicalReport(technicalReport1);
                sectionsService.addSection(sections);
            }

            //添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","技术报告",technicalReport.getId(),technicalReport.getName());
            loggerUserDao.addLog(loggerUser);
        }

        return result > 0 ;
    }

    @Transactional
	public boolean deleteTechnicalReport(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

		//查出关联的科技资讯的id
        String sectionId=sectionsService.getIdByFirstId(id,"1");
        //若是存在，再删除
        if (sectionId!=null&&!("".equals(sectionId))){
            //删除科技专栏
            sectionsService.deleteSection(sectionId);
        }

        //添加日志
        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","技术报告",id,technicalReportDao.getTechnicalReportById(id).getName());
        loggerUserDao.addLog(loggerUser);

		return technicalReportDao.deleteTechnicalReport(id);
	}

    /**
     把技术报告信息封装到技术资讯实体中,注意：此处没有设置id
     */
	private Sections addSectionByTechnicalReport(TechnicalReport technicalReport){
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

        return sections;
    }
}

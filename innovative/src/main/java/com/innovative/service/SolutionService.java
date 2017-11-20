package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.Sections;
import com.innovative.bean.Solution;
import com.innovative.bean.User;
import com.innovative.dao.*;
import com.innovative.utils.PageInfo;
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
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    IntegralService integralService;
    @Autowired
    private SectionsService sectionsService;
    @Autowired
    private LoggerUserDao loggerUserDao;
    @Autowired
    UserService userService;

    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    public Solution getSolutionById(String id) {

    	Solution solution = solutionDao.getSolutionById(id);
		if(solution!=null){
			//文件图片我们都改到一张专门的表来存储
		   List<String> urllist = fileDao.getPhotoByMOdAndId(id, "programPhoto");
		   if(urllist != null && urllist.size() > 0 )
			   solution.setPictures(urllist.get(0));
		   User user = userService.getUser(solution.getCreatedBy());
 		  if(user!=null){
  			integralService.managerIntegral(5, solution.getCreatedBy(), solution.getId());
 		  }
		}
		return solution;

    }

    /**
     * 分页条件查询方案list
     *
     * @param pageNum  页码
     * @param sectors 
     * @return
     */
    public Map<String, Object> getSolutionListByCondition(int pageNum, String sectors) {


    	  if (!StringUtils.isEmpty(sectors)) {
              sectors = "{" + sectors + "}";
          }

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Solution> items = solutionDao.getSolutionListByCondition( offset, limit,sectors);
        for(Solution so : items){
        	if(null == so|| "".equals(so.getId()))
        		continue;
        	 List<String> urllist = fileDao.getPhotoByMOdAndId(so.getId(), "programPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			   so.setPictures(urllist.get(0));
        }
        int totalCount = solutionDao.getCountByCondition(sectors);

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
     * 新增技术报告,同时将信息添加到科技专栏
     *
     * @param solution 参数集合
     * @return
     */
    @Transactional
    public boolean insertSolution(Solution solution) {

        int result = solutionDao.insertSolution(solution);
        //增加成功
        if(result>0){
            //添加到科技专栏
            Sections sections=tranSolutionToSection(solution);
            sectionsService.addSection(sections);
            //设置为审批通过
            String sectionId=sectionsService.getIdByFirstId(solution.getId(),"2");
            sections.setState("1");
            sections.setId(sectionId);
            sectionsService.updateSection(sections);
            //添加积分
            integralService.managerIntegral(11, solution.getCreatedBy(), solution.getId());
        	//添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","方案",solution.getId(),solution.getName());
        	loggerUserDao.addLog(loggerUser);
        }
		 return fileDao.updateFile(solution.getId());
    }

    /**
     * 修改技术报告信息
     * @param solution 参数集合
     * @return
     */
    @Transactional
    public boolean updateSolution(Solution solution) {
    	 //删除之前的文件
        fileDao.deleteFiles(solution.getId(),"programFile");
        //新增新的文件
    	fileDao.updateFile(solution.getId());
        int result = solutionDao.updateSolution(solution);

        //若技术方案修改成功，再修改科技专栏
        if (result>0){
            //查询修改后的方案信息
            Solution solution1=solutionDao.getSolutionById(solution.getId());
            //查询对应的科技专栏id
            String sectionId=sectionsService.getIdByFirstId(solution.getId(),"2");

            if (sectionId!=null&&!("".equals(sectionId))){
                //修改科技专栏
                System.err.print("xiugai"+solution1);
                Sections sections=tranSolutionToSection(solution1);
                sections.setId(sectionId);
                sectionsService.updateSection(sections);
            }else {
                //添加科技资讯
                Sections sections=tranSolutionToSection(solution1);
                sectionsService.addSection(sections);
                //设置为审批通过
                sections.setState("1");
                sections.setId(sectionId);
                sectionsService.updateSection(sections);
            }

            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","方案",solution.getId(),solution.getName());
            loggerUserDao.addLog(loggerUser);
        }

        return result > 0 ;
    }

    @Transactional
	public boolean deleteSolution(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

		/*//删除科技专栏
        String sectionId=sectionsService.getIdByFirstId(id,"2");
        sectionsService.deleteSection(sectionId);*/

        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","方案",id,solutionDao.getSolutionById(id).getName());
        loggerUserDao.addLog(loggerUser);
		return solutionDao.deleteSolution(id);
	}

    /**
     * 把技术方案信息封装到科技专栏实体
     * @param solution
     * @return
     */
    private Sections tranSolutionToSection(Solution solution){
        Sections sections=new Sections();
        sections.setTitle(solution.getName());
        sections.setResource("内部资源/方案");
        sections.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sections.setSectors(solution.getSectors());
        sections.setTags(solution.getTags());
        sections.setResume(solution.getSummary());
        sections.setCotent(solution.getContent());
        sections.setCreateBy(solution.getCreatedBy());
        sections.setImgid(solution.getPictures());
        sections.setFirstid(solution.getId());
        sections.setType("2");

        return  sections;
    }
}

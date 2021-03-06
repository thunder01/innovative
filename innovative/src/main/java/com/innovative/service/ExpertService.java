package com.innovative.service;

import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Expert;
import com.innovative.bean.Logger;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.User;
import com.innovative.dao.ExpertDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.LoggerDao;
import com.innovative.dao.LoggerUserDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpertService {

    @Autowired
    ExpertDao expertDao;
    @Autowired
    CodeItemUtil codeItemUtil;
    @Autowired
    FileDao fileDao;
    @Autowired
    IntegralService integralService;
    @Autowired
	LoggerUserDao loggerUserDao;
    @Autowired
    UserService userService;
  
    


    /**
     * 根据id获取专家详情
     * @param id 专家id
     * @return
     */
    public Expert getExpert(String id){
    	
        		Expert expert =	expertDao.getExpert(id);
        		if(expert!=null){
        		   List<String> url = fileDao.getPhotoByMOdAndId(id, "expertPhoto");
        		   if(url != null && url.size() > 0 )
        			  	expert.setAvatar( url.get(0));
	        		List<Map<String,Object>> statusList = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",expert.getCooperationStatus());
	        		if(statusList!=null&&statusList.size()>0)
	        			expert.setCooperationStatusMap(statusList.get(0));
	        		User user = userService.getUser(expert.getCreatedBy());
	        		if(user!=null){
	        			integralService.managerIntegral(5, expert.getCreatedBy(), expert.getId());
	        		}
        		}
        		return expert;
    }
    



    /**
     * 专家列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getExpertList(String sectors, Integer pageNum){

        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Expert> experts = expertDao.getExpertList(sectors, pageInfo.getStartIndex(), pageInfo.getPageSize());
        for(Expert e: experts){
        	if(e==null || "".equals(e.getId()))
        		continue;
        	List<String> url = fileDao.getPhotoByMOdAndId(e.getId(), "expertPhoto");
  		   if(url != null && url.size() > 0 )
  			  	e.setAvatar( url.get(0));
        }
        int totalCount = expertDao.getTotalCount(sectors);

        Map<String, Object> map = new HashMap<>();
        /*map.put("experts", experts);
        map.put("totalCount", totalCount);
        map.put("expertCount", experts.size());
        map.put("pageSize()", pageInfo.getPageSize());
        map.put("currentPageNum()", pageInfo.getCurrentPageNum());*/
        
        
        map.put("items", experts);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }



    /**
     * 添加专家
     * @param params 新增参数
     * @return
     */
	@Transactional
    public boolean addExpert(Map<String, Object> params) {
    	boolean flag=expertDao.addExpert(params) > 0;
        return flag;
    }




    /**
     * 修改专家信息
     * @param expert 修改参数
     * @return
     */
    @Transactional
    public boolean updateExpert(Expert expert) {
    	fileDao.updateFile(expert.getId());
        boolean flag =	(expertDao.updateExpert(expert) > 0) ;
        if (flag){
        	LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","专家",expert.getId(),expert.getName());
			loggerUserDao.addLog(loggerUser);
        }
		return flag ;
    }



    @Transactional
	public boolean addForExpert(Expert expert) {
    	int num = expertDao.addForExpert(expert);
    	if(num>0){
    		integralService.managerIntegral(7, expert.getCreatedBy(), expert.getId());
			//添加日志
			LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","专家",expert.getId(),expert.getName());
    		loggerUserDao.addLog(loggerUser);
    	}
    	fileDao.updateFile(expert.getId());
		 return num>0 ? true  : false;
	}



    public Map<String, Object> getExpertLists(Integer page, String sectors, String keyword) {
		 if (!StringUtils.isEmpty(sectors)) {
	            sectors = "{" + sectors + "}";
	        }
		 String key1 = "%" + keyword + "%".trim();
	     String key2 = "{" + keyword + "}".trim();
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Expert> experts = expertDao.getExpertLists( pageInfo.getStartIndex(), pageInfo.getPageSize(),sectors,key1,key2);
	       int totalCount = expertDao.getTotalCountNum(sectors,key1,key2);
	       for(Expert e: experts){
	        	if(e==null || "".equals(e.getId()))
	        		continue;
	        	List<String> url = fileDao.getPhotoByMOdAndId(e.getId(), "expertPhoto");
	  		   if(url != null && url.size() > 0 )
	  			  	e.setAvatar( url.get(0));
	        }
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", experts);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
		 return map;
	}



	@Transactional
	public boolean deleteExpert(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

		LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","专家",id,expertDao.getExpert(id).getName());
		loggerUserDao.addLog(loggerUser);
		return expertDao.deleteExpert(id);
	}



}

package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.Organization;
import com.innovative.bean.User;
import com.innovative.dao.FileDao;
import com.innovative.dao.LoggerUserDao;
import com.innovative.dao.OrganizationDao;
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
public class OrganizationService {
    @Autowired
    OrganizationDao organizationDao;
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
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    public Organization getOrganization(String id){
    		Organization organization =	organizationDao.getOrganization(id);
    		if(organization!=null){
    			//文件图片我们都改到一张专门的表来存储
    		   List<String> url = fileDao.getPhotoByMOdAndId(id, "addunionPhoto");
    		   if(url != null && url.size() > 0 )
    			   organization.setLogo( url.get(0));
        		List<Map<String,Object>> statusList = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",organization.getCooperationStatus());
        		if(statusList!=null&&statusList.size()>0)
        			organization.setCooperationStatusMap(statusList.get(0));
        		User user = userService.getUser(organization.getCreatedBy());
	       		  if(user!=null){
	        			integralService.managerIntegral(5, organization.getCreatedBy(), organization.getId());
	       		  }
    		}
    		return organization;
    }



    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param pageNum 页数（默认为1）
     * @param sectors 
     * @param keyword 
     * @return
     */
    public Map<String, Object> getOrganizationList(Integer pageNum, String sectors, String keyword){

        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        String key1 = "%" + keyword + "%".trim();
	    String key2 = "{" + keyword + "}".trim();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Organization> organizations = organizationDao.getOrganizationList( pageInfo.getStartIndex(), pageInfo.getPageSize(),sectors,key1,key2);
        for (Organization or : organizations){
        	if(null ==  or || "".equals(or.getId()))
        		continue;
        	 List<String> url = fileDao.getPhotoByMOdAndId(or.getId(), "addunionPhoto");
  		   if(url != null && url.size() > 0 )
  			   or.setLogo( url.get(0));
        }
        int totalCount = organizationDao.getTotalCount(sectors,key1,key2);

        Map<String, Object> map = new HashMap<>();
        map.put("items", organizations);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        
        return map;
    }


    /**
     * 添加机构信息
     * @param organization 新增参数
     * @return
     */
    @Transactional
    public boolean addOrganization(Organization organization) {
        //增加成功
        int result = organizationDao.addOrganization(organization);
        if(result>0){
        	integralService.managerIntegral(8, organization.getCreatedBy(), organization.getId());
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","合作机构",organization.getId(),organization.getName());
            loggerUserDao.addLog(loggerUser);
        }
		 return fileDao.updateFile(organization.getId());
    }



    /**
     * 修改机构信息
     * @param organization 修改参数
     * @return
     */
    public boolean updateOrganization(Organization organization) {
    	fileDao.updateFile(organization.getId());
    	boolean flag=organizationDao.updateOrganization(organization) > 0;

    	if (flag){
    	    LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","合作机构",organization.getId(),organization.getName());
    	    loggerUserDao.addLog(loggerUser);
        }

        return flag;
    }



	public boolean deleteOrganization(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","合作机构",id,organizationDao.getOrganization(id).getName());
        loggerUserDao.addLog(loggerUser);

		return organizationDao.deleteOrganization(id);
	}




}

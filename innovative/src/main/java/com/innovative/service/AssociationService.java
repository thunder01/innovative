package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Association;
import com.innovative.bean.Logger;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.User;
import com.innovative.dao.AssociationDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.LoggerUserDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import net.sf.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssociationService {


    @Autowired
    AssociationDao associationDao;
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
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    public Association getAssociation(String id){

         
    	Association association	= associationDao.getAssociation(id);
	       
			if(association!=null){
				List<String> urllist = fileDao.getPhotoByMOdAndId(id, "indusinfoPhoto");
     		   if(urllist != null && urllist.size() > 0 )
     			 association.setLogo( urllist.get(0));
				List<Map<String,Object>> statuslist = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",association.getCooperationStatus());
				if(statuslist!=null&&statuslist.size()>0)
					association.setCooperationStatusMap(statuslist.get(0));
				User user = userService.getUser(association.getCreatedBy());
        		if(user!=null){
        			integralService.managerIntegral(5, association.getCreatedBy(), association.getId());
        		}
			}
			return association;
    }



    /**
     * 行业协会列表页
     * @param pageNum 页数（默认为1）
     * @param sectors 
     * @param keyword 
     * @return
     */
    public Map<String, Object> getAssociationList(Integer pageNum, String sectors, String keyword){

         sectors = "{" + sectors + "}";
        String key1 = "%" + keyword + "%".trim();
	     String key2 = "{" + keyword + "}".trim();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Association> associations = associationDao.getAssociationList( pageInfo.getStartIndex(), pageInfo.getPageSize(),sectors,key1,key2);
        for(Association ass : associations){
        	if(ass.getId()==null || "".equals(ass.getId()))
        		continue;
        	List<String> urllist = fileDao.getPhotoByMOdAndId(ass.getId(), "indusinfoPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			 ass.setLogo( urllist.get(0));
        }
        int totalCount = associationDao.getTotalCount(sectors,key1,key2);

        Map<String, Object> map = new HashMap<>();
        map.put("items", associations);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }




    /**
     * 添加行业协会
     * @param association 新增参数
     * @return
     */
    @Transactional
    public boolean addAssociation(Association association) {

        //增加成功
    	  int result = associationDao.addAssociation(association);
    	  if(result>0){
    		  integralService.managerIntegral(9, association.getCreatedBy(), association.getId());
              //日志记录
    		  LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","行业协会",association.getId(),association.getName());
              loggerUserDao.addLog(loggerUser);
    	  }
    		  
		 return fileDao.updateFile(association.getId());
    }

    /**
     * 修改行业协会
     * @param association 修改参数
     * @return
     */
    @Transactional
    public boolean updateAssociation(Association association) {
    	 fileDao.updateFile(association.getId());
    	 int flag=associationDao.updateAssociation(association);
    	 if (flag > 0){
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","行业协会",association.getId(),association.getName());
            loggerUserDao.addLog(loggerUser);
    	 }
        return flag > 0;
    }


    /**
     * 根据id删除行业协会
     * @param id
     * @return
     */
    @Transactional
	public boolean deleteAssociation(String id) {
		// TODO Auto-generated method stub
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
        fileDao.deleteFile(id);
        LoggerUser loggerUser2=new LoggerUser(MDC.get("userid"),"删除","行业协会",id,associationDao.getAssociation(id).getName());
        loggerUserDao.addLog(loggerUser2);
        return associationDao.deleteAssociation(id);
	}
}

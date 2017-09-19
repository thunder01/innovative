package com.innovative.service;

import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Expert;
import com.innovative.dao.ExpertDao;
import com.innovative.dao.FileDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
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
    public boolean addExpert(Map<String, Object> params) {
    	
    	
        return (expertDao.addExpert(params) > 0);
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
         return flag ;
    }



    @Transactional
	public boolean addForExpert(Expert expert) {
    	int num = expertDao.addForExpert(expert);
    	fileDao.updateFile(expert.getId());
		 return num>0 ? true  : false;
	}




	public Map<String, Object> getExpertLists(Integer page) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Expert> experts = expertDao.getExpertLists( pageInfo.getStartIndex(), pageInfo.getPageSize());
	       int totalCount = expertDao.getTotalCountNum();
		
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", experts);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
		 return map;
	}



}

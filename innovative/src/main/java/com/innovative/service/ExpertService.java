package com.innovative.service;

import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Expert;
import com.innovative.dao.ExpertDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpertService {

    @Autowired
    ExpertDao expertDao;
    @Autowired
    CodeItemUtil codeItemUtil;
  
    


    /**
     * 根据id获取专家详情
     * @param id 专家id
     * @return
     */
    public Expert getExpert(Integer id){
    	
        		Expert expert =	expertDao.getExpert(id);
        		List<Map<String,Object>> statusMap = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",expert.getCooperationStatus());
        		expert.setCooperationStatusMap(statusMap.get(0));
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
        map.put("experts", experts);
        map.put("totalCount", totalCount);
        map.put("expertCount", experts.size());
        map.put("pageSize()", pageInfo.getPageSize());
        map.put("currentPageNum()", pageInfo.getCurrentPageNum());
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
    public boolean updateExpert(Expert expert) {
        return (expertDao.updateExpert(expert) > 0);
    }




	public boolean addForExpert(Expert expert) {
		/*String username = CookiesUtil.getCookieValue("user_uid");
		if(username!=null&&username.length()>0)
			expert.setCreatedBy(username);*/
		
		 return (expertDao.addForExpert(expert) > 0);
	}



}

package com.innovative.service;


import com.innovative.bean.Association;
import com.innovative.dao.AssociationDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssociationService {


    @Autowired
    AssociationDao associationDao;
    @Autowired
    CodeItemUtil codeItemUtil;


    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    public Association getAssociation(Integer id){

         
    	Association association	= associationDao.getAssociation(id);
	        List<Map<String,Object>> statusMap = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",association.getCooperationStatus());
			if(statusMap!=null)
				association.setCooperationStatusMap(statusMap.get(0));
			return association;
    }



    /**
     * 行业协会列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getAssociationList(Integer pageNum){

       /* if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }*/
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Association> associations = associationDao.getAssociationList( pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = associationDao.getTotalCount();

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
    public boolean addAssociation(Association association) {

        return (associationDao.addAssociation(association) > 0);
    }




    /**
     * 修改行业协会
     * @param association 修改参数
     * @return
     */
    public boolean updateAssociation(Association association) {
    	
        return (associationDao.updateAssociation(association) > 0);
    }







}

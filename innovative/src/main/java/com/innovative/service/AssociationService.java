package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Association;
import com.innovative.dao.AssociationDao;
import com.innovative.utils.CookiesUtil;
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


    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    public Association getAssociation(Integer id){

        return associationDao.getAssociation(id);
    }



    /**
     * 行业协会列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getAssociationList(String sectors, Integer pageNum){

        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Association> associations = associationDao.getAssociationList(sectors, pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = associationDao.getTotalCount(sectors);

        Map<String, Object> map = new HashMap<>();
        map.put("associations", associations);
        map.put("totalCount", totalCount);
        map.put("associationCount", associations.size());
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

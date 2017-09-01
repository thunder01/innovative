package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Organization;
import com.innovative.dao.OrganizationDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {


    @Autowired
    OrganizationDao organizationDao;

    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    public Organization getOrganization(Integer id){
        return organizationDao.getOrganization(id);
    }



    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getOrganizationList(String sectors, Integer pageNum){

        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Organization> organizations = organizationDao.getOrganizationList(sectors, pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = organizationDao.getTotalCount(sectors);

        Map<String, Object> map = new HashMap<>();
        map.put("organizations", organizations);
        map.put("totalCount", totalCount);
        map.put("organizationCount", organizations.size());
        return map;
    }


    /**
     * 添加机构信息
     * @param params 新增参数
     * @return
     */
    public boolean addOrganization(Map<String, Object> params) {
        return (organizationDao.addOrganization(params) > 0);
    }



    /**
     * 修改机构信息
     * @param params 修改参数
     * @return
     */
    public boolean updateOrganization(Map<String, Object> params) {
        return (organizationDao.updateOrganization(params) > 0);
    }




}

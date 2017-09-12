package com.innovative.service;


import com.innovative.bean.Organization;
import com.innovative.dao.OrganizationDao;
import com.innovative.utils.CodeItemUtil;
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
    @Autowired
    CodeItemUtil codeItemUtil;

    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    public Organization getOrganization(Integer id){
    		Organization organization =	organizationDao.getOrganization(id);
    		List<Map<String,Object>> statusMap = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",organization.getCooperationStatus());
			if(statusMap!=null)
				organization.setCooperationStatusMap(statusMap.get(0));
        return organization;
    }



    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getOrganizationList(Integer pageNum){

        /*if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }*/
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Organization> organizations = organizationDao.getOrganizationList( pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = organizationDao.getTotalCount();

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
    public boolean addOrganization(Organization organization) {
        return (organizationDao.addOrganization(organization) > 0);
    }



    /**
     * 修改机构信息
     * @param organization 修改参数
     * @return
     */
    public boolean updateOrganization(Organization organization) {
        return (organizationDao.updateOrganization(organization) > 0);
    }




}

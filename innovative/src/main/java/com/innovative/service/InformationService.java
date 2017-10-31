package com.innovative.service;


import com.innovative.bean.Information;
import com.innovative.dao.InformationDao;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 科技专栏
 * @author cj
 *
 */
@Service
public class InformationService {

    @Autowired
    private InformationDao informationDao;
/**
 * 添加科技资讯
 * @param sections
 * @return
 */
	public boolean addInformation(Information information) {
		// TODO Auto-generated method stub
		information.setId(Misc.base58Uuid());
		return informationDao.addInformation(information);
	}
/**
 * 修改科技资讯
 * @param sections
 * @return
 */
	public boolean updateInformation(Information information) {
		// TODO Auto-generated method stub
		return informationDao.updateInformation(information);
	}
	/**
	 * 根据id查询科技专栏
	 * @param id
	 * @return
	 */
	public Information getInformationById(String id) {
		// TODO Auto-generated method stub
		return informationDao.getInformationById(id);
	}
	/**
	 * 查询科技专栏列表
	 * @param page
	 * @return
	 */
	public  Map<String, Object> getInformationLists(Integer page) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Information> informations = informationDao.getInformationLists( pageInfo.getStartIndex(), pageInfo.getPageSize());
	       int totalCount = informationDao.getTotalCountNum();
		
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", informations);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
	        return map;
	}
	/**
	 * 删除推特信息
	 * @param id
	 * @return
	 */
	public boolean deleteInformation(String id) {
		// TODO Auto-generated method stub
		return informationDao.deleteInformation(id);
	}


}

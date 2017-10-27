package com.innovative.service;


import com.innovative.bean.Expert;
import com.innovative.bean.Sections;
import com.innovative.dao.SectionsDao;
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
public class SectionsService {

    @Autowired
    private SectionsDao sectionsDao;
/**
 * 添加科技资讯
 * @param sections
 * @return
 */
	public boolean addSection(Sections sections) {
		// TODO Auto-generated method stub
		sections.setId(Misc.base58Uuid());
		return sectionsDao.addSection(sections);
	}
/**
 * 修改科技资讯
 * @param sections
 * @return
 */
	public boolean updateSection(Sections sections) {
		// TODO Auto-generated method stub
		return sectionsDao.updateSection(sections);
	}
	/**
	 * 根据id查询科技专栏
	 * @param id
	 * @return
	 */
	public Sections getSectionById(String id) {
		// TODO Auto-generated method stub
		return sectionsDao.getSectionById(id);
	}
	/**
	 * 查询科技专栏列表
	 * @param page
	 * @return
	 */
	public  Map<String, Object> getSectionLists(Integer page) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Sections> sections = sectionsDao.getSectionsLists( pageInfo.getStartIndex(), pageInfo.getPageSize());
	       int totalCount = sectionsDao.getTotalCountNum();
		
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", sections);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
	        return map;
	}
	/**
	 * 删除科技专栏
	 * @param id
	 * @return
	 */
	public boolean deleteSection(String id) {
		// TODO Auto-generated method stub
		return sectionsDao.deleteSection(id);
	}


}

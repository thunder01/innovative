package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.innovative.bean.Collect;
import com.innovative.dao.CollectDao;
import com.innovative.utils.PageInfo;

@Service
public class CollectService {
	@Resource
	private CollectDao collectDao;
	/**
	 * 添加征集即我要征集
	 * @param collection
	 */
	public Map<String, Object> saveCollection(Collect collection){
		int result = collectDao.saveCollection(collection);
		Map<String, Object> map = listCollection(1);
		map.put("result", result);//0是失败，1是成功
		return map;
	}
	/**
	 * 分页查询全员征集
	 * @param pageNum
	 * @return
	 */
	public Map<String , Object> listCollection(Integer pageNum){
		Map<String , Object> map=new HashMap<>();		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
		List<Collect> list = collectDao.findAllCollection(pageInfo.getStartIndex(), pageInfo.getPageSize());
		int total = collectDao.findTotal();
		map.put("items", list);
		map.put("totalCount", total);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize()); 
		return map;
	}
	
	public Map<String , Object> findCollectionById(Integer id){
		Map<String , Object> map=new HashMap<>();
		Collect collect = collectDao.findCollectionById(id);
		map.put("collcet", collect);
		return map;
	}
}

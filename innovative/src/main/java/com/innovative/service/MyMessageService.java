package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.innovative.bean.Demand;
import com.innovative.dao.DemandDao;
import com.innovative.utils.PageInfo;

@Service
public class MyMessageService {
	@Resource
	private DemandDao demandDao;
	/**
	 * 我的需求
	 * @param userid
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getMyDemand(String userid,Integer pageNum){
		Map<String, Object> map=new HashMap<>();
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
		List<Demand> list = demandDao.getMyselfDemand(pageInfo.getStartIndex(), pageInfo.getPageSize(), userid);
		int total = demandDao.getMyselfDemandTotal(userid);
		map.put("items", list);
		map.put("userid", userid);  
		map.put("totalCount", total);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
}

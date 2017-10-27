package com.innovative.service;

import com.innovative.bean.Informationpushcomenter;
import com.innovative.dao.InformationpushcomenterDao;
import com.innovative.utils.Config;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationpushcomenterService {

    @Autowired
    InformationpushcomenterDao informationpushcomenterDao;
  
    


/**
 * 根据推特信息获取所有评论
 * @param pushId 推特信息id
 * @return
 */
public List<Informationpushcomenter> getInformationpushcomenterByPushId(String pushId) {
	// TODO Auto-generated method stub
	return informationpushcomenterDao.getInformationpushcomenterByPushId(pushId);
}

/**
 * 删除评论信息
 * @param id 评论id
 * @return
 */
public boolean deleteInformationpushcoment(String id) {
	// TODO Auto-generated method stub
	return informationpushcomenterDao.deleteInformationpushcoment(id);
}
/**
 * 增加一条评论或回复信息
 * @param informationpushcomenter
 * @return
 */
public boolean addInformationpushcomenter(Informationpushcomenter informationpushcomenter) {
	// TODO Auto-generated method stub
	return informationpushcomenterDao.addInformationpushcomenter(informationpushcomenter);
}
/**
 * 根据id获取评论信息
 * @param id 评论id
 * @return
 */
public Informationpushcomenter getformationpushcomenter(String id) {
	// TODO Auto-generated method stub
	return informationpushcomenterDao.getformationpushcomenter(id);
}
/**
 * 修改评论信息
 * @param informationpushcomenter
 * @return
 */
public boolean updateInformationpushcomenter(Informationpushcomenter informationpushcomenter) {
	// TODO Auto-generated method stub
	return informationpushcomenterDao.updateInformationpushcomenter(informationpushcomenter);
}

/**
 * 查找用户评论记录
 * @param userid
 * @param page
 * @return
 */
public Map<String,Object> getInformationPushComentersByUserid(String comentBy, Integer page) {
	Map<String,Object> map = new HashMap<String,Object>();
	PageInfo pageInfo = new PageInfo();
	pageInfo.setPageSize(Config.COLLCT_NUM);
    pageInfo.setCurrentPageNum(page);
    List<Map<String,Object>> collectlist =  informationpushcomenterDao.getInformationPushComentersByUserid(comentBy,pageInfo.getStartIndex(),pageInfo.getPageSize());
	Integer collctNum = informationpushcomenterDao.getTotalCountByUserId(comentBy);
        map.put("items", collectlist);
        map.put("totalCount", collctNum);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
	
	 return map ;
}



}

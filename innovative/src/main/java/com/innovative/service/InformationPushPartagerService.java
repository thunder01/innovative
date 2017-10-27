package com.innovative.service;

import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Expert;
import com.innovative.bean.InformationPushPartager;
import com.innovative.bean.Informationpushcomenter;
import com.innovative.dao.InformationPushPartagerDao;
import com.innovative.dao.InformationpushDao;
import com.innovative.dao.InformationpushcomenterDao;
import com.innovative.utils.Config;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationPushPartagerService {

    @Autowired
    InformationPushPartagerDao informationPushPartagerDao;
    @Autowired
    InformationpushDao informationPushDao;
  
    







/**
 * 增加一条分享信息
 * @param informationPushPartager
 * @return
 */
public boolean addInformationPushPartager(InformationPushPartager informationPushPartager) {
	// TODO Auto-generated method stub
	//用户今天是否分享过
	Integer num = informationPushPartagerDao.isTodayPartagerInfornaionPush(informationPushPartager.getPushId(),informationPushPartager.getPartagerBy());
	if(num == 0){
		informationPushPartagerDao.addInformationPushPartager(informationPushPartager);
	}
	else{
		return false;
	}
	return true;
}



/**
 * 分页获取分享记录
 * @param partagerBy
 * @param page
 * @return
 */
public Map<String, Object> getInformationpushPartagersByUserid(String partagerBy, Integer page) {
    PageInfo pageInfo = new PageInfo();
    pageInfo.setCurrentPageNum(page);
    pageInfo.setPageSize(Config.COLLCT_NUM);

    List<Map<String,Object>> nformationpushPartagers = informationPushPartagerDao.getInformationpushPartagersByUserid(partagerBy, pageInfo.getStartIndex(), pageInfo.getPageSize());
    Integer totalCount = informationPushPartagerDao.getTotalCountByUserId(partagerBy);

    Map<String, Object> map = new HashMap<>();
    map.put("items", nformationpushPartagers);
    map.put("totalCount", totalCount);
    map.put("Count", pageInfo.getPageSize());
    map.put("itemCount", pageInfo.getPageSize());
    map.put("offset", pageInfo.getStartIndex());
    map.put("limit", pageInfo.getPageSize());
    return map;
	
}


/**
 * 查询一条推特信息的所有的分享记录
 * @param pushId
 * @return
 */
public List<InformationPushPartager> getInformationpushPartagersByPushid(String pushId) {
	// TODO Auto-generated method stub
	return informationPushPartagerDao.getInformationpushPartagersByPushid(pushId);
}






}

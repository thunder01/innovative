package com.innovative.service;

import com.innovative.bean.Informationpush;
import com.innovative.bean.Informationpushcomenter;
import com.innovative.dao.InformationpushDao;
import com.innovative.dao.InformationpushcomenterDao;
import com.innovative.utils.Config;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationpushcomenterService {

    @Autowired
    InformationpushcomenterDao informationpushcomenterDao;
    @Autowired
    MessageService messageService;
    @Autowired
    InformationpushDao informationpushDao;
  
  
    


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
@Transactional
public boolean addInformationpushcomenter(Informationpushcomenter informationpushcomenter) {
	//推送消息
	Informationpush informationpush = informationpushDao.getInformationpushById(informationpushcomenter.getPushId());
	//如果是评论就把这条消息推送给推特消息的发布者
    if(informationpushcomenter.getPid() == null ||"".equals(informationpushcomenter.getPid())){
    	//增加消息推送（这条推特信息的主人推送消息）
        messageService.insertMessage(informationpush.getComentBy(), informationpushcomenter.getId(), Config.TT_PL, 1);
        messageService.updateMsgCount(informationpush.getComentBy());
    }
	
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
/**
 * 获取评论信息（带出推特信息  推特信息发布人     评论人     评论内容）
 * @param id 评论id
 * @param  userid
 * @return
 */
public Informationpush getInformationpushcomenterForMessage(String id, String userid) {
	return informationpushcomenterDao.getInformationpushcomenterForMessage(id,userid);
}



}

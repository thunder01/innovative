package com.innovative.service;

import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.bean.MsgCount;
import com.innovative.dao.DemandDao;
import com.innovative.dao.MessageDao;
import com.innovative.dao.MsgCountDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MsgCountDao msgCountDao;
	@Autowired
	private DemandDao demandDao;
	/**
	 * 添加容
	 */
	public boolean addMessage(Message message) {
		return (messageDao.addMessage(message) > 0 ? true : false);
	}

	/**
	 * 行列表页
	 * 
	 * @param pageNum
	 *            页数（默认为1）
	 * @return
	 */
	public Map<String, Object> getMessageList(Integer pageNum) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNum(pageNum);
		List<Message> demands = messageDao.getMessageList(pageInfo.getStartIndex(), pageInfo.getPageSize());
		int totalCount = messageDao.getTotalCount();
		Map<String, Object> map = new HashMap<>();
		map.put("items", demands);
		map.put("totalCount", totalCount);
		map.put("Count", pageInfo.getPageSize());
		map.put("itemCount", pageInfo.getPageSize());
		map.put("offset", pageInfo.getStartIndex());
		map.put("limit", pageInfo.getPageSize());
		return map;
	}

	/**
	 * 修改状态
	 */
	public boolean upStatus(int id) {
		return (messageDao.upStatus(id) > 0 ? true : false);
	}
	/*           消息表更新分割线                      */
	
	
	/**
	 *  * 获取信息列表
	 * @param message 消息Bean  需要userid和notice值
	 * @param pageNum 页数
	 * @return
	 */
	@Transactional
	public Map<String,Object> getMessage(Message message,Integer pageNum){
		Map<String,Object> map = new HashMap<>();
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNum(pageNum);
		//消息的集合
		List<Message> list = messageDao.showNoticeMessage
				(message.getUserid(), message.getNotice(), pageInfo.getStartIndex(), pageInfo.getPageSize());
		MsgCount msgCount = msgCountDao.showMsgCount(message.getUserid());
		MsgCount msg = new MsgCount();
		msg.setUserid(message.getUserid());
		if(msgCount!=null){
			msg.setMessage_count(msgCount.getMessage_count());
			msg.setOld_message_count(msgCount.getOld_message_count());
			msg.setNotice_count(msgCount.getNotice_count());
			msg.setUnfinish_count(msgCount.getUnfinish_count());
			msg.setFinish_count(msgCount.getFinish_count());
			msg.setOld_notice_count(msgCount.getOld_notice_count());
			msg.setOld_unfinish_count(msgCount.getOld_unfinish_count());
			msg.setOld_finish_count(msgCount.getOld_finish_count());
		}
		
		if(message.getNotice()==1){//消息里面的通知
			if(msgCount!=null){
				//需要判断消息的类型  0是下单审批，1是拆解报告确认，2是团队评价
				for (Message m : list) {
					if("0".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
					if("1".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);					
					}
					if("2".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
				}
				map.put("items", list);
				map.put("msgCount", msg);
				msgCount.setOld_notice_count(messageDao.totalNoticeMessage(message.getUserid(), 1));
				msgCountDao.updateMsgCount(msgCount);
			}
		}
		if(message.getNotice()==2){//消息里面的已办
			if(msgCount!=null){
				//需要判断消息的类型  0是下单审批，1是拆解报告确认，2是团队评价
				for (Message m : list) {
					if("0".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
					if("1".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);					
					}
					if("2".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
				}
				map.put("items", list);
				map.put("msgCount", msg);
				msgCount.setOld_finish_count(messageDao.totalNoticeMessage(message.getUserid(), 2));
				msgCountDao.updateMsgCount(msgCount);
			}
		}
		if(message.getNotice()==3){//消息里面的待办
			if(msgCount!=null&&list.size()>0){
				//需要判断消息的类型  0是下单审批，1是拆解报告确认，2是团队评价
				for (Message m : list) {
					if("0".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
					if("1".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);					
					}
					if("2".equals(m.getType())){
						Demand demand = demandDao.getDemand(m.getProject_id());
						m.setObject(demand);
					}
				}
				map.put("items", list);
				map.put("msgCount", msg);
				msgCount.setOld_unfinish_count(messageDao.totalNoticeMessage(message.getUserid(), 3));
				msgCountDao.updateMsgCount(msgCount);
			}
		}
		return map;
	}
	/**
	 * 消息表里的数据有变化时(添加和修改notice的类型)需要更新MsgCount数据库的数据信息
	 * @return
	 */
	@Transactional
	public int updateMsgCount(String userid){
		int notice_count = messageDao.totalNoticeMessage(userid, 1);
		int finish_count = messageDao.totalNoticeMessage(userid, 2);
		int unfinish_count = messageDao.totalNoticeMessage(userid, 3);
		MsgCount msg =new MsgCount();
		msg.setUserid(userid);
		msg.setNotice_count(notice_count);
		msg.setFinish_count(finish_count);
		msg.setUnfinish_count(unfinish_count);
		
		MsgCount msgCount = msgCountDao.showMsgCount(userid);
		if(msgCount==null){
			//向MsgCount中添加一个数据
			msgCountDao.saveMsgCount(userid);
		}else{
			msg.setOld_notice_count(msgCount.getOld_notice_count());
			msg.setOld_finish_count(msgCount.getOld_finish_count());
			msg.setUnfinish_count(msgCount.getOld_unfinish_count());
		}
		//更新MsgCount
		int result = msgCountDao.updateMsgCount(msg);
		return result;
	}
	
	/**
	 * 
	 * @param userid 消息发给谁
	 * @param proid 目标的id
	 * @param type 类型  (0需求下单     1拆解报告	2项目评价	 3情报)谁添加不一样的消息类型，自己加上注释
	 * @param notice 1是通知，2是已办，3是待办
	 * @return
	 */
	@Transactional
	public int insertMessage(String userid,String proid,String type,int notice){
		Message message = new Message();
		message.setUserid(userid);
		message.setProid(proid);
		message.setType(type);
		message.setNotice(notice);
		int result = messageDao.saveMessage(message);
		if(result!=0){
			updateMsgCount(userid);
			return 1;
		}
		return 0;
	}
	
	
	
}

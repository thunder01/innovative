package com.innovative.service;

import com.innovative.bean.Demand;
import com.innovative.bean.Information;
import com.innovative.bean.Informationpush;
import com.innovative.bean.Intelligence;
import com.innovative.bean.Message;
import com.innovative.bean.MsgCount;
import com.innovative.bean.Order;
import com.innovative.bean.Sections;
import com.innovative.bean.TechInformationCollection;
import com.innovative.bean.TechSectionsCollection;
import com.innovative.bean.User;
import com.innovative.dao.ApprouverDao;
import com.innovative.dao.CollectionDao;
import com.innovative.dao.DemandDao;
import com.innovative.dao.InformationDao;
import com.innovative.dao.InformationPushPartagerDao;
import com.innovative.dao.InformationpushcomenterDao;
import com.innovative.dao.IntelligenceDao;
import com.innovative.dao.MessageDao;
import com.innovative.dao.MsgCountDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.SectionsDao;
import com.innovative.dao.TechInformationCollectionDao;
import com.innovative.dao.TechSectionsCollectionDao;
import com.innovative.dao.UserDao;
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
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private IntelligenceDao intelligenceDao;
	@Autowired
	private ApprouverDao approuverDao;
	@Autowired
	private InformationpushcomenterDao informationpushcomenterDao;
	@Autowired
	private CollectionDao collectionDao;
	@Autowired
	private InformationPushPartagerDao informationPushPartagerDao;
	@Autowired
	private SectionsDao sectionsDao;
	@Autowired
	private InformationDao informationDao;
	@Autowired
	private TechSectionsCollectionDao techSectionsCollectionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TechInformationCollectionDao techInformationCollectionDao;
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
	/*  ***********************************消息表更新分割线 ***************************/
	
	
	/**
	 * 获取信息列表(0需求下单、1拆解报告、2项目评价、3情报、4推特信息点赞、5 推特信息转发、6 推特信息收藏、7推特信息评论、8科技专栏审核、9科技资讯审核、10科技专栏修改、
	 *     11科技资讯修改 、12科技资讯收藏 、13科技专栏收藏  )
	 * @param message 消息Bean  需要userid和notice值
	 * @param pageNum 页数
	 * @return		
	 */
	@Transactional
	public Map<String,Object> getMessage(Message message,Integer pageNum){
		Map<String,Object> map = new HashMap<>();
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPageNum(pageNum);
		map.put("limit", pageInfo.getPageSize());
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
		map.put("msgCount", msg);
		if(message.getNotice()==1){//消息里面的通知
			if(msgCount!=null){
				//需要判断消息的类型  0是下单审批，1是拆解报告确认，2是团队评价
				for (Message m : list) {
					if("0".equals(m.getType())){//0是下单审批
						Demand demand = demandDao.getDemand(Integer.parseInt(m.getProid()));
						m.setObject(demand);
					}
					if("1".equals(m.getType())){//1是拆解报告确认
//						Demand demand = demandDao.getDemand(Integer.parseInt(m.getProid()));
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemand(order.getDemandId());
						demand.setOrder(order);
						m.setObject(demand);				
					}
					if("2".equals(m.getType())){//2是团队评价
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemand(order.getDemandId());
						demand.setOrder(order);
						m.setObject(demand);
					}
					if("3".equals(m.getType())){//3情报
						Intelligence intelligence = intelligenceDao.getIntelligence(Integer.parseInt(m.getProid()));
						m.setObject(intelligence);
					}
					if("4".equals(m.getType())){//4推特信息点赞
						Informationpush informationpush = approuverDao.getApprouverByIdForMessage(m.getProid(), message.getUserid());
						m.setObject(informationpush);
					}
					if("5".equals(m.getType())){//5 推特信息转发
						Informationpush informationpush = informationPushPartagerDao.getInformationpushPartagersByIdForMessage(m.getProid(), m.getUserid());
						m.setObject(informationpush);
					}
					if("6".equals(m.getType())){//6 推特信息收藏
						Informationpush informationpush = collectionDao.getCollectInformationForMessage(m.getProid(), message.getUserid());
						m.setObject(informationpush);
					}
					if("7".equals(m.getType())){//7推特信息评论
						Informationpush informationpush = informationpushcomenterDao.getInformationpushcomenterForMessage(m.getProid(), message.getUserid());
						m.setObject(informationpush);
					}
					if("8".equals(m.getType())){//8科技专栏审核
						Sections sections = sectionsDao.getSectionById(m.getProid());
						m.setObject(sections);
					}
					if("9".equals(m.getType())){//9科技资讯审核
						Information information = informationDao.getInformationById(m.getProid(), m.getUserid());
						m.setObject(information);
					}
					if("10".equals(m.getType())){//10科技专栏修改
						Sections sections = sectionsDao.getSectionById(m.getProid());
						m.setObject(sections);
					}
					if("11".equals(m.getType())){//11科技资讯修改
						Information information = informationDao.getInformationById(m.getProid(), m.getUserid());
						m.setObject(information);
					}
					if("12".equals(m.getType())){//12科技资讯收藏 
						TechInformationCollection techInformationCollection = techInformationCollectionDao.getById(m.getProid());
						Information information = informationDao.getInformationById(techInformationCollection.getInformationId(), m.getUserid());
						User user = userDao.getUser(techInformationCollection.getCollectBy());
						techInformationCollection.setUser(user);
						techInformationCollection.setInformation(information);
						m.setObject(techInformationCollection);
					}
					if("13".equals(m.getType())){//13科技专栏收藏 
						TechSectionsCollection techSectionsCollection = techSectionsCollectionDao.getById(m.getProid());
						Sections sections = sectionsDao.getSectionById(techSectionsCollection.getSectionId());
						techSectionsCollection.setSections(sections);
						User user = userDao.getUser(techSectionsCollection.getCollectBy());
						techSectionsCollection.setUser(user);
						m.setObject(techSectionsCollection);
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
					if("0".equals(m.getType())){//0是下单审批
						Demand demand = demandDao.getDemand(Integer.parseInt(m.getProid()));
						m.setObject(demand);
					}
					if("1".equals(m.getType())){//1是拆解报告确认
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemandByOrderid(Integer.parseInt(m.getProid()));
						demand.setOrderid(order.getId());
						demand.setOrder(order);
						m.setObject(demand);
					}
					if("2".equals(m.getType())){//2是团队评价
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemand(order.getDemandId());
						demand.setOrder(order);
						m.setObject(demand);
					}
					if("3".equals(m.getType())){//3情报
						Intelligence intelligence = intelligenceDao.getIntelligence(Integer.parseInt(m.getProid()));
						m.setObject(intelligence);
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
				for (Message m : list) {//0是下单审批
					if("0".equals(m.getType())){
						Demand demand = demandDao.getDemand(Integer.parseInt(m.getProid()));
						m.setObject(demand);
					}
					if("1".equals(m.getType())){//1是拆解报告确认
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemand(order.getDemandId());
						demand.setOrder(order);
						m.setObject(demand);					
					}
					if("2".equals(m.getType())){//2是团队评价
						Order order = orderDao.getOrderById(Integer.parseInt(m.getProid()));
						Demand demand = demandDao.getDemand(order.getDemandId());
						demand.setOrder(order);
						m.setObject(demand);
					}
					if("3".equals(m.getType())){//3情报
						Intelligence intelligence = intelligenceDao.getIntelligence(Integer.parseInt(m.getProid()));
						m.setObject(intelligence);
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
			msg.setOld_unfinish_count(msgCount.getOld_unfinish_count());
		}
		//更新MsgCount
		int result = msgCountDao.updateMsgCount(msg);
		return result;
	}
	
	/**
	 * 
	 * @param userid 消息发给谁
	 * @param proid 目标的id
	 * @param type 类型  (0需求下单、1拆解报告、2项目评价、3情报、4推特信息点赞、5 推特信息转发、6 推特信息收藏、7推特信息评论、8 科技专栏审核、9 科技资讯审核、10 科技专栏修改)谁添加不一样的消息类型，自己加上注释
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
	
	/**
	 * 待办更新成已办
	 * @param userid
	 * @param id
	 * @return
	 */
	public int updateMessage(String userid,Integer id){//待办更新成已办
		int result = messageDao.updateNotice(id, userid);
		return result;
	}
	/**
     * 通过消息的类型和资源的id来找消息
     * @param type 消息的类型
     * @param proid 资源id
     * @return
     */
	public Message getMessageByTypeAndProid(String type,String proid){
		Message message = messageDao.getMessageByTypeAndProid(type, proid);
		return message;
	}
	
	
}

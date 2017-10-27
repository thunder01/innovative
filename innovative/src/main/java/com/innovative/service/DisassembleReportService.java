package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innovative.bean.Demand;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.FileBean;
import com.innovative.bean.Message;
import com.innovative.bean.Order;
import com.innovative.bean.User;
import com.innovative.dao.DemandDao;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.MessageDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.UserDao;


/**
 * 拆解报告的业务逻辑类
 * @author fzy
 * @version 2.0
 * */
@Service
public class DisassembleReportService {
	@Autowired
	private DisassembleReportDao reportDao;//注入持久层接口
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private MessageDao messageDao;//消息
	@Autowired
	private UserDao userDao;
	@Autowired
	private DemandDao demandDao;
	@Autowired 
	private FileDao fileDao;
	
	/**
	 * 拆解报告上传之后，将上传记录添加到数据库，并向消息表添加一条记录
	 * @param repor 拆解报告信息
	 * @param orderid 订单id
     * @return
	 * */
	@Transactional
	public Map<String, Object> saveDisassembleReport(DisassembleReport report,Integer orderid){
		Map<String, Object> map=new HashMap<>();
		fileDao.updateFile(report.getFileid());
		
		if ("1".equals(report.getMessage())) {
			map.put("orderid", orderid);
		}else{
			/*先将次订单的所有拆解报告删除*/
			Integer disassembleID=reportDao.getIdByOrderId(orderid);
			if (disassembleID!=null) {
				reportDao.deleteDisassembleReportByIdReal(disassembleID);
			}
			
			/*保存拆解报告*/
			int result = reportDao.saveDisassembleReport(report);	
			/*根据订单id查询需求id*/
			int projectId=orderDao.getDemandIdByOrderId(orderid);
			
			/*生成消息信息*/
			Message message=new Message();
			message.setProjectId(projectId);
			message.setType("1");
			
			/*发送消息*/
			messageDao.addMessage(message);
			
			map.put("result",result);
			map.put("orderid", orderid);
		}
		return map;	 
	}
	
	/**
	 * 删除拆解报告，逻辑上删除
	 * @param dReport
	 * @return
	 */
	public Map<String, Object> deleteDisassembleReportById(DisassembleReport dReport){
		Map<String, Object> map=new HashMap<>();
		int result=0;
		if (dReport!=null) {
			result=reportDao.deleteDisassembleReportById(dReport.getId());
			
		}
		map.put("result", result);
		map.put("item", dReport);
		return map;
	}
	
	/**
	 * 根据id查询拆解报告的详情
	 * @param disid
	 * @param orderid
	 * @return
	 */
	public Map<String, Object> getDisassembleReportById(Integer disid,Integer orderid){
		Map<String, Object> map=new HashMap<>();	
		DisassembleReport dReport=reportDao.getDisassembleReportById(disid);
		
		/*获取文件id*/
		String fileid=dReport.getFileid();
		List<FileBean> listFiles=fileDao.getFileById(fileid, "disassemble");
		dReport.setList(listFiles);
		
		map.put("disid", dReport.getId());
		map.put("item", dReport);
		map.put("orderid", orderid);	
		return map;
	}
	
	/**
	 * 修改拆解报告
	 * @param disassembleReport
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateDisassembleReportById(DisassembleReport disassembleReport){
		Map<String, Object> map=new HashMap<>();
		String field=disassembleReport.getFileid();//拆解报告文件的id
		fileDao.updateFile(field);//更新文件
		int result=reportDao.updateDisassembleReportById(disassembleReport);//修改拆解报告
		
		List<FileBean> listFiles=fileDao.getFileById(field, "disassemble");
		DisassembleReport report = reportDao.getDisassembleReportById(disassembleReport.getId());//根据id查询拆解报告
		report.setList(listFiles);
		
		map.put("result", result);
		map.put("item",report);
		map.put("orderid", report.getOrder_id());
		map.put("disid", report.getId());
		return map;
	}
	
	/**
	 * 跳转到拆解报告确认页面，查询出拆解报告的相关信息
	 * @param demand_id
	 * @return
	 */
	public Map<String, Object> confirmDisassemble(Integer demand_id){
		Map<String, Object> map=new HashMap<>();	
		Demand demand=demandDao.getDemand(demand_id);//根据需求id查询出需求信息	
		/*1、根据需求id查出订单id*/
		int orderid=orderDao.getOrderIdByDemandId(demand_id);	
		/*2、根据订单id查出拆解报告id*/
		int disid=reportDao.getIdByOrderId(orderid);	
		/*3、根据拆解报告id查询拆解报告信息*/
		DisassembleReport dReport=reportDao.getDisassembleReportById(disid);
		/*4、根据创建人的id，查出创建人的信息*/
		User user=userDao.getUser(dReport.getCreate_by());
		
		/*5、获取文件id*/
		String fileid=dReport.getFileid();
		List<FileBean> listFiles=fileDao.getFileById(fileid, "disassemble");
		dReport.setList(listFiles);
		
		map.put("item", dReport);
		map.put("demandname",demand.getName());
		map.put("user", user);
		map.put("contact", demand.getIphone());
		return map;
	}
	
	/**
	 * 确认拆解报告通过与否
	 * @param disid 拆解报告id
	 * @param status 通过状态 ：0 未通过，1 通过
	 * @return
	 */
	@Transactional
	public Map<String, Object> confirmDisassembleStatus(DisassembleReport dReport){
		Map<String, Object> map=new HashMap<>();
		System.out.println("参数:"+dReport);
		Integer dReportID=dReport.getId();
		/*根据id查出拆解报告信息*/
		DisassembleReport report=reportDao.getDisassembleReportById(dReportID);
		Integer order_id=report.getOrder_id();
		/*未通过*/
		if ("0".equals(dReport.getStatus2())) {
			/*先判断拆解报告是否已经通过*/
			Order order = orderDao.getOrderById(order_id);
			if (order.getPass_status()==0) {//判断这个订单是否已经进行过拆解报告确认操作，若已经确认过则不能再次确认
				/*1、将订单信息删除*/
				orderDao.deleteByOrderId(order_id);
				/*2、删除拆解报告*/
				reportDao.deleteDisassembleReportByIdReal(dReportID);
			}
			map.put("message", "拆解报告未通过");
		}		
		/*通过*/
		if ("1".equals(dReport.getStatus())) {
			/*将订单的confirm_status置为1*/
			orderDao.updateConfirm_status(order_id);			
			/*将订单的pass_status置为1*/
			orderDao.updatePass_status(order_id,dReport.getPass_by());
			map.put("message", "拆解报告通过");
		}		
		int demand_id=orderDao.getDemandIdByOrderId(order_id);
		Demand demand=demandDao.getDemand(demand_id);//查询需求信息	
		User user=userDao.getUser(report.getCreate_by());//查询拆解报告创建人的信息
		
		map.put("item", report);
		map.put("user", user);
		map.put("contact", demand.getIphone());
		return map;
	}
	
	/**
	 * 跳转上传确认，判断拆解报告是否已经上传过
	 * @param orderid
	 * @return
	 */
	public Map<String, Object> toUpload(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		if (reportDao.getDisassembleByOrderid(orderid)!=null) {
			map.put("message", "1");//已经上传
		}else{
			map.put("message", "0");//未上传过
		}
		
		map.put("orderid", orderid);
		return map;
	}
}

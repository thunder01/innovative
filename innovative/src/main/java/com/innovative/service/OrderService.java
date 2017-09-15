package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.Report;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.ReportDao;


/**
 * 处理订单业务逻辑
 * @author fzy
 * */
@Service
public class OrderService {
	@Autowired 
	private OrderDao orderDao;
	@Autowired
	private ProjectApprovalDao projectApprovalDao;
	@Autowired
	private DisassembleReportDao disassembleReportDao;
	@Autowired
	private ReportDao reportDao;
	
	/**
	 * 新增订单信息
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public int insertOrder(Integer userid,Integer demandid){
		//首先查询该需求是否可接
		
		//若可接则生成订单，否则返回"此需求已被接单"
		Order order = new Order();
		order.setCreate_byId(userid);
		order.setDemandId(demandid);
		return orderDao.insertOrder(order);
	}
	
	/**
	 * 寻源工程师下单操作，更新已有的订单
	 * @param order 订单对象
	 * @return
	 * */
	@Transactional
	public int updateOrderLate_byId(Integer userid,Integer approvalId){	
		/*根据立项表单id,查询状态(0是为接单，1是已接单)*/
		int status=projectApprovalDao.getProjectApprovalStatusById(approvalId);
		if (1==status) {
			return 0;
		}else {//未接单
			int orderId=orderDao.selectOrderIdByApproval(approvalId);//根据立项id查出订单id	
			Order order = new Order();
			order.setId(orderId);//补全订单信息
			order.setLate_byId(userid);
			/*首先更新订单表中的立项表单id信息*/
			orderDao.updateOrderLate_byId(order);
			/*将立项表单的状态置为1*/
			return projectApprovalDao.updateProjectApprovalStatus(approvalId);
		}	
	}
	
	/**
	 * 根据立项表单的id,查找出对应的订单id
	 * @param id 立项表单id
	 * @return
	 * */
	public int selectOrderIdByApproval(Integer id){
		return orderDao.selectOrderIdByApproval(id);
	}
	
	/**
	 * 根据当前用户id查询我的订单
	 * @param id 用户id
	 * @return
	 * */
	public List<Order> selectMyOrder(Integer id){
		/*首先判断用户的角色,根据用户角色选择订单*/
		//String role=userService.findRole(id);
		//if ("需求工程师".equals(role)) {
			return orderDao.selectDemandByUserId(id);
		//}
		
		//if ("寻源工程师".equals(role)) {
			//return orderDao.selectSourceByUserId(id);
		//}
	}
	
	/**
	 * 根据订单id，查询订单信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Order selectOrderById(Integer orderid){
		return orderDao.selectOrderById(orderid);
	}
	
	/**
	 * 根据订单id查询对应的需求id
	 * @param orderid 订单id
	 * @return
	 * */
	public int selectDisassemble(Integer orderid){
		return orderDao.selectDisassemble(orderid);
	}
	
	/**
	 * 根据定单id，查询出立项表单 的id
	 * @param order
	 * @return
	 * */
	public int selectApproval(Integer orderid){
		return orderDao.selectApproval(orderid);
	}
	/**
	 * 通过订单id查询需求工程师和寻源工程师
	 * @param demand_id
	 * @return
	 */
	public Map<String, Object> getTeamByDemand_id(Integer demand_id){
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = orderDao.selectOrderByDemand_id(demand_id);
		if(null!=order){
			ProjectApproval projectApproval = projectApprovalDao.getProjectApprovalById(order.getId());
			map.put("需求工程师", projectApproval.getCreate_by());
			List<Report> list = reportDao.rankReport(order.getId());
			if(null!=list &&list.size()>0){
				map.put("寻源工程师", list.get(0).getCreate_by());
			}
		}
		return map;
	}
	/**
	 * 通过需求id来获得需求工程师上传的文件和时间
	 * @param demand_id
	 * @return
	 */
	public Map<String, Object> getDemandFile(Integer demand_id){
		Order order = orderDao.selectOrderByDemand_id(demand_id);
		Map<String, Object> map = new HashMap<String, Object>();
//		ProjectApproval projectApproval = projectApprovalDao.getProjectApprovalById(order.getId());
		if(null!=order){
			DisassembleReport disassembleReport = disassembleReportDao.getDisassembleReportById(order.getDisassembleId());
			map.put("拆解报告上传时间", disassembleReport.getCreate_date());
			map.put("拆解报告的File", disassembleReport.getFile());
		}
//		map.put("立项表单的上传时间", projectApproval.getCreate_date());
//		map.put("立项表单的File", projectApproval.get);
		return map;
	}
	/**
	 * 通过需求id来查询寻源工程师上传的文件和时间
	 * @param demand_id
	 * @return
	 */
	public List<Report> getReportFiles(Integer demand_id){
		Order order = orderDao.selectOrderByDemand_id(demand_id);
		List<Report> list =null;
		if(null!=order){
			list = reportDao.rankReport(order.getId());
		}
		return list;
	}
}

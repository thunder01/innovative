package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innovative.bean.Demand;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.Report;
import com.innovative.bean.User;
import com.innovative.dao.DemandDao;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.ReportDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.PageInfo;


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
	@Autowired
	private DemandDao demandDao;
	@Autowired UserDao userDao;
	
	/**
	 * 新增订单信息 需求池
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public Map<String, Object> insertOrder(String userid,Integer demandid){
		//首先查询该需求是否可接
		
		Demand demand=demandDao.getDemand(demandid);
		//若可接则生成订单，否则返回"此需求已被接单"
		Order order = new Order();
		order.setCreate_byId(userid);
		order.setDemandId(demandid);
		
		Map<String, Object> map=new HashMap<>();
		map.put("demand", demand);
		map.put("userid", userid);
		map.put("result", orderDao.insertOrder(order));
		return map;
	}
	
	/**
	 * 寻源工程师下单操作，更新已有的订单
	 * @param order 订单对象
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> updateOrderLate_byId(String late_byId,Integer approvalId){	
		Map<String, Object> map=new HashMap<>();
		/*根据立项表单id,查询状态(0是为接单，1是已接单)*/
		int status=projectApprovalDao.getProjectApprovalStatusById(approvalId);
		if (1==status) {
			return (Map<String, Object>) map.put("result", 0);
		}else {//未接单
			int orderId=orderDao.selectOrderIdByApproval(approvalId);//根据立项id查出订单id	
			Order order = new Order();
			order.setId(orderId);//补全订单信息，订单id
			order.setLate_byId(late_byId);//接单人
			
			/*首先更新订单表中的寻源工程师id信息（late_byId）*/
			int result=orderDao.updateOrderLate_byId(order);
			map.put("result", result);
			/*将立项表单的状态置为1*/
			projectApprovalDao.updateProjectApprovalStatus(approvalId);
			
			map.put("orderid", orderId);
		
			return map;
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
	public Map<String,Object> selectMyOrder(String id,Integer pageNum){
		Map<String , Object> map=new HashMap<>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		/*首先判断用户的角色,根据用户角色选择订单*/
		User user=userDao.getUser(id);
		
		List<Order> list=orderDao.selectDemandByUserId(id);
		for(Order order:list){
			Demand demand=demandDao.getDemand(order.getDemandId());
			order.setDemand(demand);
		}
			
		map.put("user", user);
		map.put("items", list);
		
        map.put("totalCount", orderDao.getTotalContent(id));      
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
	
	/**
	 * 根据订单id，查询订单信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Map<String, Object> selectOrderById(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		
		Order order=orderDao.selectOrderById(orderid);//根据订单id查询出订单信息
		if(order!=null){
			if(order.getDemandId()!=null){
				Demand demand=demandDao.getDemand(order.getDemandId());
				if(demand!=null){
					order.setDemand(demand);
					map.put("result", 1);//结果正确
				}				
			}
			User user=userDao.getUser(order.getCreate_byId());
			map.put("item", order);
			map.put("user", user);
			map.put("orderid", orderid);
		}else{
			map.put("result", 0);//结果error
		}
		return map;
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

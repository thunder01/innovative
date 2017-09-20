package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innovative.bean.Demand;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
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
	private DemandDao demandDao;
	@Autowired 
	private UserDao userDao;
	
	/**
	 * 新增订单信息 需求池
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public Map<String, Object> insertOrder(String userid,Integer demandid){
		Map<String, Object> map=new HashMap<>();
	
		//首先查询该需求是否可接
		Demand demand=demandDao.getDemand(demandid);
		
		if (demand!=null/*&&demand.get接单状态==0*/ ) {
			//若可接则生成订单,并将需求的接单状态改为1
			/*demandDao.updateDemand(demand);*/
			
			int flag=orderDao.insertOrder(demandid,userid);
			if (flag!=0) {
				/*demandDao.update接单状态;*/
				map.put("demand", demand);
				map.put("userid", userid);
			}else{
				map.put("message", "生成订单失败");
			}
		}else{
			map.put("message", "此需求已经有人接单");
		}		
		return map;
	}
	
	/**
	 * 根据当前用户id查询我的订单
	 * @param id 用户id
	 * @param pageNum分页信息
	 * @return
	 * */
	public Map<String,Object> selectMyOrder(String userid,Integer pageNum){
		Map<String , Object> map=new HashMap<>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		/*首先根据用户id获取用户信息*/
		User user=userDao.getUser(userid);
		
		if (user!=null) {
			String role=user.getUserPost();//获取用户角色
			if ("需求工程师".equals(role)) {
				/*根据用户id获取其所有的订单*/
				List<Order> list=orderDao.selectOrderListByUserId(userid,pageInfo.getStartIndex(), pageInfo.getPageSize());
				map.put("totalCount", orderDao.getTotalCOuntMyorder(userid));
				if (list.size()>0) {
					map.put("items", list);
				}else{
					map.put("message", "您还没有订单信息");
				}		
			}else if ("寻源工程师".equals(role)) {
				/*查出其所有接过的立项表单*/
				List<ProjectApproval> list=projectApprovalDao.selectApprovalListByUserId(userid,pageInfo.getStartIndex(), pageInfo.getPageSize());
				map.put("totalCount", projectApprovalDao.getSourceCount(userid));
				if (list.size()>0) {
					map.put("items", list);
				}else{
					map.put("message", "您还没有接过订单");
				}
			}else {
				map.put("message", "不是相关角色");
			}
			/*for(Order order:list){
				Demand demand=demandDao.getDemand(order.getDemandId());
				order.setDemand(demand);
			}*/
		}else{
			map.put("message", "用户不存在");
		}
		map.put("user", user);     
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
	public Map<String, Object> selectOrderByOrderId(Integer orderid){
		Map<String, Object> map=new HashMap<>();

		Order order=orderDao.selectOrderByOrderId(orderid);//根据订单id查询出订单信息
		if(order!=null){
			User user=userDao.getUser(order.getCreate_byId());
			map.put("item", order);
			map.put("user", user);
			map.put("orderid", orderid);
			map.put("result", 1);
		}else{
			map.put("result", 0);//结果为空
		}
		return map;
	}
	
	/**
	 * 根据订单id获取拆解报告信息和立项表单列表
	 * */
	public Map<String, Object> getDisassembleAndApprovalListByOrderid(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		
		/*根据订单id查出拆解报告信息*/
		DisassembleReport disassembleReport=disassembleReportDao.getDisassembleByOrderid(orderid);	
		if (disassembleReport!=null) {
			map.put("disassemble", disassembleReport);
		}else{
			map.put("message1", "没有拆解报告");
		}
		
		/*根据订单id查询所有的立项表单*/
		List<ProjectApproval> list=projectApprovalDao.getApprovalListByOrderid(orderid);
		if (list.size()>0) {
			map.put("approvalList", list);
		}else{
			map.put("message2", "没有立项表单");
		}
		
		map.put("orderid", orderid);
		return map;
	}
	
	
}

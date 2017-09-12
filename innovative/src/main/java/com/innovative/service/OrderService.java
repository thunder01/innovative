package com.innovative.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Order;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;


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
	
	/**
	 * 新增订单信息
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public int insertOrder(Order order){
		return orderDao.insertOrder(order);
	}
	
	/**
	 * 寻源工程师下单操作，更新已有的订单
	 * @param order 订单对象
	 * @return
	 * */
	@Transactional
	public int updateOrderLate_byId(Order order,Integer approvalId){
		/*首先将立项表单的状态更改为已接单*/
		projectApprovalDao.updateProjectApprovalStatus(approvalId);
		return orderDao.updateOrderLate_byId(order);//插入订单数据
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
}
